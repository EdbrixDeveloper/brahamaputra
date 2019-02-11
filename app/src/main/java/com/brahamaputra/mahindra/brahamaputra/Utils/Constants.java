package com.brahamaputra.mahindra.brahamaputra.Utils;

public class Constants {

    public final static String contentType_C = "C";
    // http://enterpriseservices.edbrix.net/app/student/authenticatestudent
    public final static String contentType_WC = "WC";
    public final static String contentType_IMG = "IMG";
    public final static String contentType_Audio = "AC";
    public final static String contentType_Video = "VC";
    public final static String contentType_Doc = "DC";
    public final static String contentType_Iframe = "IC";
    public final static String contentType_Survey = "SV";
    public final static String contentType_Test = "TEST";
    public final static String contentType_Section = "sec";
    public final static String contentType_TrainingSession = "TS";
    public final static String contentType_Assignment = "AS";
    public final static String contentType_Event = "E";
    public final static String docContentType_File = "file";
    public final static String docContentType_Img = "img";
    public final static String submitType_Check = "check";
    public final static String submitType_Timer = "timer";
    public final static String submitType_Question = "question";
    public final static String submitDataType_TrueFalse = "truefalse";
    public final static String submitDataType_SingleChoice = "singlechoice";
    public final static String submitDataType_MultiChoice = "multichoice";
    public final static String submitDataType_ImageChoice = "imagechoice";
    public final static String submitDataType_LongAnswer = "longanswer";
    public final static String availabilityType_TrainingSession = "TS";
    public final static String availabilityType_ZOOM = "ZOOM";
    public final static String TolkBox_APIKey = "45467242";
    public final static String TolkBox_Token = "Token";
    public final static String TolkBox_SessionId = "SessionId";
    public final static String TolkBox_MeetingId = "MeetingId";
    public final static String TolkBox_MeetingName = "MeetingName";
    public final static String TolkBox_MeetingType = "MeetingType";
    public final static String FileType_Playwire = "playwire";
    public final static String FileType_Document = "document";
    public static int meetingUserCount = 0;
    public static String hototicket_Selected_SiteType = "";
    public static String hototicket_Selected_CustomerName = "";
    public static String hototicket_nameOfSupplyCompany = "";

    public static String hototicket_sourceOfPower = "";

    // TODO Change it to your web domain
    public final static String WEB_DOMAIN = "http://mahindradeveb2-api-env.srumj83md3.ap-south-1.elasticbeanstalk.com/";
    // TODO Change it to your APP Key
    public final static String APP_KEY__ = "QVBAMTIjMllIRC1TREFTNUQtNUFTRksyMjExMA==";
    // TODO Change it to your APP Secret
    public final static String APP_SECRET__ = "MjQ1QDEyIzJZSEQtODVEQTJTM0RFQTg1Mz1JRTVCNEE1MTA=";
    // TODO change it to your user ID
    final static String USER_ID = "604813"; // 604813  ijTMthVYQzSy06ba9Elxdw


    //private final static String BASE_URL = "http://brahmaputra-dev-api.us-west-2.elasticbeanstalk.com/";
    private final static String BASE_URL = "http://mahindradeveb2-api-env.srumj83md3.ap-south-1.elasticbeanstalk.com/";

    public final static String userLogin = BASE_URL.concat("auth/login");  // authenticatestudent
    public final static String hototTicketList = BASE_URL.concat("hototickets/gethototticketlist");  // gethototticketlist
    public final static String submitHototTicket = BASE_URL.concat("hototickets/submithotottickettransactions");  // submitHototticket http://brahmaputra-dev-api.us-west-2.elasticbeanstalk.com/hototickets/submithotottickettransactions
    public final static String hototticketstatusclockin = BASE_URL.concat("hototickets/hototticketstatusclockin");  // hototticketstatusclockin
    public final static String changepassword = BASE_URL.concat("auth/changepassword");  // changepassword
    public final static String editprofile = BASE_URL.concat("auth/editprofile");  // editprofile
    public final static String forgotpassword = BASE_URL.concat("auth/forgotpassword");  // forgotpassword


    public final static String GetUserSites = BASE_URL.concat("common/getusersites"); //* // getusersites //used in diesel filling activity
    public final static String GetDgId_from_Sites = BASE_URL.concat("common/getdgmrqrcodesbysiteid");//*  // getdgmrqrcodesbysiteid //used in diesel filling activity
    public final static String GetSitePowerBackupDgData = BASE_URL.concat("common/getsitepowerbackupdgdata");//used in diesel filling activity for KVA reading
    public final static String GetSiteDieselTransactionByDG = BASE_URL.concat("common/getsitedieseltransactionbydg");

    public final static String Submitdieselfillingfundrequesttransaction = BASE_URL.concat("diesel/createdieselfillingrequest");// Submitdieselfillingfundrequesttransaction //used in diesel filling fund request activity
    public final static String getuserdieselrequestticketlist = BASE_URL.concat("diesel/getuserdieselrequestticketlist");//used in Diesel Filling Fund ReqestList List activity
    public final static String getUserApprovedDieselRequestTicketList = BASE_URL.concat("diesel/getuserapproveddieselrequestticketlist");//used for getting approved diesel request ticket list
    public final static String Submitdieselfillingtransaction = BASE_URL.concat("diesel/submitdieselfillingtransaction");  // Submitdieselfillingtransaction //used in diesel filling activity
    public final static String Getdieseltransactionticketlist = BASE_URL.concat("diesel/getdieseltransactionticketlist");//used in diesel filling List activity

    //EB Process webservices
    public final static String GetCustomer = BASE_URL.concat("common/getcustomers");
    public final static String GetCircle = BASE_URL.concat("common/getcircles");
    public final static String GetState = BASE_URL.concat("/common/getstates");
    public final static String GetSSA = BASE_URL.concat("common/getssa");
    public final static String GetSite = BASE_URL.concat("common/getsites");//*
    public final static String GetSiteSelectConnectiondata = BASE_URL.concat("common/getsiteselectricconnectiondata");//*
    public final static String SubmitElectricityBillTicketList = BASE_URL.concat("electricbill/submitebfillingtransaction");
    public final static String GetElectriBillTransactionslist = BASE_URL.concat("electricbill/getelectribilltransactionslist");
    public final static String SubmitEbfillingPaymentEeceipt = BASE_URL.concat("electricbill/submitebfillingpaymentreceipt");
    public final static String GetUserDetails = BASE_URL.concat("common/getuserdetails");
    public final static String GetEbPaymentDetails = BASE_URL.concat("electricbill/getelectribillchequedddetails");
    public final static String SubmitEbPaymentDetails = BASE_URL.concat("electricbill/submitebfillingddchequereceipt");
}
