/*================================================================================
Copyright (c) 2008 VMware, Inc. All Rights Reserved.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, 
this list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice, 
this list of conditions and the following disclaimer in the documentation 
and/or other materials provided with the distribution.

* Neither the name of VMware, Inc. nor the names of its contributors may be used
to endorse or promote products derived from this software without specific prior 
written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
IN NO EVENT SHALL VMWARE, INC. OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
POSSIBILITY OF SUCH DAMAGE.
================================================================================*/

package com.vmware.vim25.mo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class CommandLineParser {
    private final Map<String, String> optsEntered = new HashMap<>();
    private final Map<String, OptionSpec> userOpts = new HashMap<>();
    private final Map<String, OptionSpec> builtInOpts = new HashMap<>();

    public CommandLineParser(OptionSpec[] userOptions, String[] args) {
        builtinOptions();

        if (userOptions != null) {
            addOptions(userOptions);
            parseInput(args);
            validate();
        } else {
            parseInput(args);
            validate();
        }
    }


    public void addOptions(OptionSpec[] userOptions) {
        for (final OptionSpec userOption : userOptions) {
            if (userOption.getOptionName() != null && userOption.getOptionName().length() > 0 &&
                    userOption.getOptionDesc() != null && userOption.getOptionDesc().length() > 0 &&
                    userOption.getOptionType() != null && userOption.getOptionType().length() > 0 &&
                    (userOption.getOptionRequired() == 0 || userOption.getOptionName().length() > 1)) {
                userOpts.put(userOption.getOptionName(), userOption);
            } else {
                System.out.println("Option " + userOption.getOptionName() + " definition is not valid");
                throw new IllegalArgumentException("Option " + userOption.getOptionName()
                        + " definition is not valid");
            }
        }
    }

    private void builtinOptions() {
        OptionSpec url = new OptionSpec("url", "String", 1, "VI SDK URL to connect to", null);
        OptionSpec userName = new OptionSpec("userName", "String", 1, "Username to connect to the host", null);
        OptionSpec password = new OptionSpec("password", "String", 1, "password of the corresponding user", null);
        OptionSpec config = new OptionSpec("config", "String", 0, "Location of the VI perl configuration file", null);
        OptionSpec protocol = new OptionSpec("protocol", "String", 0, "Protocol used to connect to server", null);
        OptionSpec server = new OptionSpec("server", "String", 0, "VI server to connect to", null);
        OptionSpec portNumber = new OptionSpec("portNumber", "String", 0, "Port used to connect to server", "443");
        OptionSpec servicePath = new OptionSpec("servicePath", "String", 0, "Service path used to connect to server", null);
        OptionSpec sessionFile = new OptionSpec("sessionFile", "String", 0, "File containing session ID/cookie to utilize", null);
        OptionSpec help = new OptionSpec("help", "String", 0, "Display user information for the script", null);
        OptionSpec ignorecert = new OptionSpec("ignorecert", "String", 0, "Ignore the server certificate validation", null);
        builtInOpts.put("url", url);
        builtInOpts.put("username", userName);
        builtInOpts.put("password", password);
        builtInOpts.put("password", password);
        builtInOpts.put("config", config);
        builtInOpts.put("protocol", protocol);
        builtInOpts.put("server", server);
        builtInOpts.put("portnumber", portNumber);
        builtInOpts.put("servicepath", servicePath);
        builtInOpts.put("sessionfile", sessionFile);
        builtInOpts.put("help", help);
        builtInOpts.put("ignorecert", ignorecert);
    }

    public void parseInput(String[] args) {
        try {
            getCmdArguments(args);
        } catch (Exception e) {
            throw new IllegalArgumentException("Exception running : " + e);
        }

        for (final String s : optsEntered.keySet()) {
            String keyValue = s;
            String keyOptions = optsEntered.get(keyValue);
            boolean result = checkInputOptions(builtInOpts, keyValue);
            boolean valid = checkInputOptions(userOpts, keyValue);
            if (!result && !valid) {
                System.out.println("Invalid Input Option '" + keyValue + "'");
                displayUsage();
                throw new IllegalArgumentException("Invalid Input Option '" + keyValue + "'");
            }
            result = checkDatatypes(builtInOpts, keyValue, keyOptions);
            valid = checkDatatypes(userOpts, keyValue, keyOptions);
            if (!result && !valid) {
                System.out.println("Invalid datatype for Input Option '" + keyValue + "'");
                displayUsage();
                throw new IllegalArgumentException("Invalid Input Option '" + keyValue + "'");
            }
        }
    }

    private void getCmdArguments(String[] args) {
        int len = args.length;
        int i = 0;
        if (len == 0) {
            displayUsage();
            throw new IllegalArgumentException("usage");
        }
        while (i < args.length) {
            String val = "";
            String opt = args[i];
            if (opt.startsWith("--") && optsEntered.containsKey(opt.substring(2))) {
                System.out.println("key '" + opt.substring(2) + "' already exists ");
                displayUsage();
                throw new IllegalArgumentException("key '" + opt.substring(2) + "' already exists ");
            }
            if (args[i].startsWith("--")) {
                if (args.length > i + 1) {
                    if (!args[i + 1].startsWith("--")) {
                        val = args[i + 1];
                        optsEntered.put(opt.substring(2), val);
                    } else {
                        optsEntered.put(opt.substring(2), null);
                    }
                } else {
                    optsEntered.put(opt.substring(2), null);
                }
            }
            i++;
        }
    }

    private boolean checkDatatypes(Map<String, OptionSpec> Opts, String keyValue, String keyOptions) {
        boolean valid = Opts.containsKey(keyValue);
        if (valid) {
            OptionSpec oSpec = Opts.get(keyValue);
            String dataType = oSpec.getOptionType();
            return validateDataType(dataType, keyOptions);
        } else {
            return false;
        }
    }

    private boolean validateDataType(String dataType, String keyValue) {
        try {
            if (dataType.equalsIgnoreCase("Boolean")) {
                return keyValue.equalsIgnoreCase("true") || keyValue.equalsIgnoreCase("false");
            } else if (dataType.equalsIgnoreCase("Integer")) {
                Integer.parseInt(keyValue);
                return true;
            } else if (dataType.equalsIgnoreCase("Float")) {
                Float.parseFloat(keyValue);
                return true;
            } else if (dataType.equalsIgnoreCase("Long")) {
                Long.parseLong(keyValue);
                return true;
            } else {
                // DO NOTHING
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean checkInputOptions(Map<String, OptionSpec> checkOptions, String value) {
        boolean valid = false;
        valid = checkOptions.containsKey(value);
        return valid;
    }

    public void validate() {
        validate(null, null);
    }

    public void validate(Object className, String functionName) {
        if (optsEntered.isEmpty()) {
            displayUsage();
            throw new IllegalArgumentException("---help");
        }
        if (optsEntered.get("help") != null) {
            displayUsage();
            System.exit(1);
        }
        if (option_is_set("help")) {
            displayUsage();
            System.exit(1);
        }
        Vector<String> vec = getValue(builtInOpts);
        for (String value : vec) {
            if (optsEntered.get(value) == null) {
                String missingArg = value;
                if (missingArg.equalsIgnoreCase("password")) {
                    String password = readPassword("Enter password: ");
                    optsEntered.put("password", password);
                } else {
                    System.out.print("----ERROR: " + value + " not specified \n");
                    displayUsage();
                    throw new IllegalArgumentException("----ERROR: " + value + " not specified \n");
                }
            }
        }
        vec = getValue(userOpts);
        for (String s : vec) {
            if (optsEntered.get(s) == null) {
                System.out.print("----ERROR: " + s + " not specified \n");
                displayUsage();
                throw new IllegalArgumentException("----ERROR: " + s + " not specified \n");
            }
        }
        if ((optsEntered.get("sessionfile") == null) &&
                ((optsEntered.get("username") == null) && (optsEntered.get("password") == null))) {
            System.out.println("Must have one of command options 'sessionfile' or a 'username' and 'password' pair\n");
            displayUsage();
            throw new IllegalArgumentException("Must have one of command options 'sessionfile' or a 'username' and 'password' pair\n");
        }
    }

    /*
     *taking out value of a particular key in the hashmap
     *i.e checking for required =1 options
     */
    private Vector<String> getValue(Map<String, OptionSpec> checkOptions) {
        final Iterator<String> it = checkOptions.keySet().iterator();
        Vector<String> vec = new Vector<>();
        while (it.hasNext()) {
            String str = it.next();
            OptionSpec oSpec = checkOptions.get(str);
            if (oSpec.getOptionRequired() == 1) {
                vec.add(str);
            }
        }
        return vec;
    }

    public void displayUsage() {
        System.out.println("Common Java Options :");
        print_options(builtInOpts);
        System.out.println("\nCommand specific options: ");
        print_options(userOpts);
    }

    private void print_options(Map<String, OptionSpec> Opts) {
        String type = "";
        String defaultVal = "";
        Iterator<String> it;
        String help = "";
        Set<String> generalKeys = Opts.keySet();
        it = generalKeys.iterator();
        while (it.hasNext()) {
            String keyValue = it.next().toString();
            OptionSpec oSpec = Opts.get(keyValue);
            if ((oSpec.getOptionType() != null) && (oSpec.getOptionDefault() != null)) {
                type = oSpec.getOptionType();
                defaultVal = oSpec.getOptionDefault();
                System.out.println("   --" + keyValue + " < type " + type + ", default " + defaultVal + ">");
            }
            if ((oSpec.getOptionDefault() != null) && (oSpec.getOptionType() == null)) {
                defaultVal = oSpec.getOptionDefault();
                System.out.println("   --" + keyValue + " < default " + defaultVal + " >");
            } else if ((oSpec.getOptionType() != null) && (oSpec.getOptionDefault() == null)) {
                type = oSpec.getOptionType();
                System.out.println("   --" + keyValue + " < type " + type + " >");
            } else if ((oSpec.getOptionType() == null) && (oSpec.getOptionDefault() == null)) {
                System.out.println("   --" + keyValue + " ");
            }
            help = oSpec.getOptionDesc();
            System.out.println("      " + help);
        }
    }

    public boolean option_is_set(String option) {
        boolean valid = false;
        for (final String s : optsEntered.keySet()) {
            String keyVal = s;
            if (option.equals(keyVal)) {
                valid = true;
                break;
            }
        }
        return valid;
    }

    public String get_option(String key) {
        if (optsEntered.get(key) != null) {
            return optsEntered.get(key);
        } else if (checkInputOptions(builtInOpts, key)) {
            if (builtInOpts.get(key).getOptionDefault() != null) {
                String str = builtInOpts.get(key).getOptionDefault();
                return str;
            } else {
                return null;
            }
        } else if (checkInputOptions(userOpts, key)) {
            if (userOpts.get(key).getOptionDefault() != null) {
                String str = userOpts.get(key).getOptionDefault();
                return str;
            } else {
                return null;
            }
        } else {
            System.out.println("undefined variable");
        }
        return null;
    }

    public String getOnelineInput(String prompt) {
        System.out.print(prompt);
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String pass = null;
        try {
            pass = stdin.readLine();
        } catch (IOException ioe) {
            System.out.println("Error in reading console input.");
        }
        return pass;
    }

    /**
     * @return web service username
     */
    public String getUsername() {
        //return _args[ARG_USER];
        return get_option("username");
    }

    /**
     * @return web service password
     */
    public String getPassword() {
        return get_option("password");
    }

    private String readPassword(String prompt) {
        try {
            PasswordMask consoleEraser = new PasswordMask();
            System.out.print(prompt);
            BufferedReader stdin = new BufferedReader(new
                    InputStreamReader(System.in));
            consoleEraser.start();
            String pass = stdin.readLine();
            consoleEraser.halt();
            System.out.print("\b");
            return pass;
        } catch (Exception e) {
            return null;
        }
    }

    static class PasswordMask extends Thread {
        private boolean running = true;

        public void run() {
            while (running) {
                System.out.print("\b ");
            }
        }

        public synchronized void halt() {
            running = false;
        }
    }
}
