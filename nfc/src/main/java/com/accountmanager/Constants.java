/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.accountmanager;

public class Constants {
    //account key
    /**
     * Account type string.
     */
    public static final String ACCOUNT_TYPE = "com.neobear.account";

    /**
     * Authtoken type string.
     */
    public static final String AUTHTOKEN_TYPE = "com.neobear.accountToken";

    //setting data key
    public static final String NEO_Child_Name = "NeoChildName";
    public static final String NEO_Child_Sex = "NeoChildSex";
    public static final String NEO_Child_Hearportrait = "NeoChildHearportrait";
    public static final String NEO_Child_Birthday = "NeoChildBirthday";

    public static final String NEO_Child_UpdateTime = "NeoChildUpdateTime";

    public static final String NEO_ChildInfo_IFSEND = "NeoChildInfo_IfSend";

   // public static final String NEO_Access_token = "Access_token";
    public static final String NEO_Expire_time = "Expire_time";
    public static final String NEO_Refresh_token = "Refresh_token";

    //login activity key
    public static final String APP_PACKAGE_NAME = "com.neobear.accountsystem";
    public static final String APP_LAUNCH_ACTIVITY_NAME = APP_PACKAGE_NAME + ".ui.login.LoginActivity";
}
