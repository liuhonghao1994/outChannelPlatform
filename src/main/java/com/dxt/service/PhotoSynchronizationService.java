package com.dxt.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.common.AppConstant;
import com.dxt.common.MyBusiException;
import com.dxt.dao.KeepOfUserMes;
import com.dxt.dao.PhotoSynchronizationDao;
import com.dxt.dao.VerificationIdCardDao;
import com.dxt.message.ReponseMessage;
import com.dxt.util.CommonUtils;
import com.dxt.util.IdCardVerification;
import com.dxt.util.ImgErToFileUtil;

import com.sun.imageio.plugins.common.ImageUtil;
import org.codehaus.xfire.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.ImageFilter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("PhotoSynchronizationService")
public class PhotoSynchronizationService extends IBaseBusiService {
    @Autowired
    private KeepOfUserMes keepOfUserMes;
    @Autowired
    private VerificationIdCardDao verificationIdCardDao;
    @Autowired
    private PhotoSynchronizationDao photoSynchronizationDao;
    private static final String PARAM_PHONE= "PHONE";
    private static final String PARAM_IDCARDFRONT= "Idcardfront";
    private static final String PARAM_REVERSEIDCARD_ICCID= "Reverseidcard_iccid";
    private static final String PARAM_BAREHEADPHOTO= "Bareheadphoto";
    private static final String PARAM_PLATFORM= "platform";
    private static final String PARAM_CARDTYPE= "cardType";
    private static final String PARAM_CARDNUMBER= "cardNumber";
    private static final String PARAM_NAME= "name";
    private static final String PARAM_CARDADDRESS= "cardAddress";
    private static final String PARAM_TAKEEFFECTOFCARDTIME= "takeEffectOfCardTime";
    private static final String PARAM_FAILUREOFCARDTIME= "failureOfCardTime";
    private static final String PARAM_ISPAS= "isPas";
    private Integer count;
    private String userid;


    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        ReponseMessage responseMessage=new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);

        try {
            checkPArms(jsonObject);
        } catch (MyBusiException e) {
            responseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    e.getMessage());
            return  responseMessage;
        }


            try {
               /* Client client = new Client(new URL("http://wx.dxt10026.com/HelloWordService/services/Hello?wsdl"));
                Map<String,String> map=new HashMap<>();
                map.put("in_custcertcode",jsonObject.getString(PARAM_CARDNUMBER));
                map.put("in_num",jsonObject.getString(PARAM_PHONE));

                //先调用存贮过程，成功直接返回结果，然后调用国政通，失败直接返回用户失败结果
                verificationIdCardDao.verificationIdCard(map);
                if (!map.get("vo_return_cod").equals("0000")){
                    responseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,AppConstant.REPONSE_MSG.SYS_REQUEST_FAIL_MSG,map.get("vo_return_message"));
                }else {
                    //调用上传照片的接口
                    userid = photoSynchronizationDao.getUUid(jsonObject.getString(PARAM_PHONE));
                    if (userid ==null){
                        responseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,AppConstant.REPONSE_MSG.SYS_REQUEST_FAIL_MSG,"用户信息不存在！");
                    }else {
                        if (jsonObject.getString(PARAM_PLATFORM).equals("11")){
                            client.invoke("uploadSignImg", new Object[]{userid,jsonObject.getString(PARAM_IDCARDFRONT) ,  "newBusiAsisstant"});
                            responseMessage.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG, "上传成功");

                        }else {

                            count = photoSynchronizationDao.getPhotos(userid);
                            if (count > 0) {
                                responseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING, AppConstant.REPONSE_MSG.SYS_REQUEST_FAIL_MSG, "已存在待审核记录,暂不能上传！");
                            } else {

                                client.invoke("uploadImgAll", new Object[]{userid,jsonObject.getString(PARAM_IDCARDFRONT) ,  jsonObject.getString(PARAM_REVERSEIDCARD_ICCID), jsonObject.getString(PARAM_BAREHEADPHOTO),jsonObject.getString(PARAM_PLATFORM)});
                                responseMessage.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG, "上传成功");


                                //当照片上传成功以后将信息保存到一张表中以供后续使用

                                //1.判断表中是否有此人信息
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String nowtime = df.format(new Date());
                                List<String> useridFromUserMes = keepOfUserMes.getUseridFromUserMes();
                                if (useridFromUserMes==null || useridFromUserMes.size()==0){
                                    keepOfUserMes.keepUserMes(userid,jsonObject.getString(PARAM_PHONE),jsonObject.getString(PARAM_CARDTYPE),jsonObject.getString(PARAM_CARDNUMBER),jsonObject.getString(PARAM_NAME),jsonObject.getString(PARAM_CARDADDRESS),jsonObject.getString(PARAM_TAKEEFFECTOFCARDTIME),jsonObject.getString(PARAM_FAILUREOFCARDTIME),nowtime,0,jsonObject.getInteger(PARAM_ISPAS));
                                }else {
                                    for (int i=0;i<useridFromUserMes.size();i++){
                                        if (useridFromUserMes.get(i).equals(userid)){
                                            //先更改以前的数据再插入数据
                                            keepOfUserMes.updateStatus(userid);
                                        }
                                    }
                                    keepOfUserMes.keepUserMes(userid,jsonObject.getString(PARAM_PHONE),jsonObject.getString(PARAM_CARDTYPE),jsonObject.getString(PARAM_CARDNUMBER),jsonObject.getString(PARAM_NAME),jsonObject.getString(PARAM_CARDADDRESS),jsonObject.getString(PARAM_TAKEEFFECTOFCARDTIME),jsonObject.getString(PARAM_FAILUREOFCARDTIME),nowtime,0,jsonObject.getInteger(PARAM_ISPAS));

                                }

                            }

                        }



                    }

                }*/
                responseMessage.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG, "上传成功");
            } catch (Exception e) {
                responseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                        e.getMessage());
                return  responseMessage;
            }


        return responseMessage;
    }

    private void checkPArms(JSONObject jsonObject) throws MyBusiException {
        if(jsonObject.getString(PARAM_PLATFORM).equals("11")){
             if ("".equals(jsonObject.getString(PARAM_PHONE)) || "".equals(jsonObject.getString(PARAM_IDCARDFRONT))    || "".equals(jsonObject.getString(PARAM_CARDTYPE)) || "".equals(jsonObject.getString(PARAM_NAME))) {
                throw new MyBusiException("缺少请求参数");
            }else if(!"2".equals(jsonObject.getString(PARAM_CARDTYPE))){
                 throw new MyBusiException("请输入正确的证件类型");
             }
            else if (!CommonUtils.inValid(jsonObject.getString(PARAM_PHONE))){
                throw new MyBusiException("号码格式不正确");
            }
            else if (CommonUtils.imageSize(jsonObject.getString(PARAM_IDCARDFRONT))>8*1024*1024){
                throw new MyBusiException("图片大小应小于8M");
            }

        }else if(jsonObject.getString(PARAM_PLATFORM).equals("9") || jsonObject.getString(PARAM_PLATFORM).equals("10")){
            boolean validDatetakeeffect = CommonUtils.isValidDate(jsonObject.getString(PARAM_TAKEEFFECTOFCARDTIME));
            boolean validDatefailure = CommonUtils.isValidDate(jsonObject.getString(PARAM_FAILUREOFCARDTIME));
            //比较证件有效期
            String format = "yyyyMMdd";
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            String format1 = sdf.format(new Date());

            if ("".equals(jsonObject.getString(PARAM_PHONE)) || "".equals(jsonObject.getString(PARAM_IDCARDFRONT)) || "".equals(jsonObject.getString(PARAM_REVERSEIDCARD_ICCID))  || "".equals(jsonObject.getString(PARAM_BAREHEADPHOTO))  || "".equals(jsonObject.getString(PARAM_CARDTYPE)) || "".equals(jsonObject.getString(PARAM_NAME)) || "".equals(jsonObject.getString(PARAM_CARDADDRESS)) || "".equals(jsonObject.getString(PARAM_TAKEEFFECTOFCARDTIME)) || "".equals(jsonObject.getString(PARAM_FAILUREOFCARDTIME))  || "".equals(jsonObject.getString(PARAM_ISPAS))) {
                throw new MyBusiException("缺少请求参数");
            }else if(!"1".equals(jsonObject.getString(PARAM_CARDTYPE))){
                throw new MyBusiException("请输入正确的证件类型");
            }
            else if (!CommonUtils.inValid(jsonObject.getString(PARAM_PHONE))){
                throw new MyBusiException("号码格式不正确");
            }
            else if (CommonUtils.imageSize(jsonObject.getString(PARAM_IDCARDFRONT))<10*1024 || CommonUtils.imageSize(jsonObject.getString(PARAM_IDCARDFRONT))>5*1024*1024){
                throw new MyBusiException("图片大小应保持10KB-5M之间");
            }
            else if (CommonUtils.imageSize(jsonObject.getString(PARAM_REVERSEIDCARD_ICCID))<10*1024 || CommonUtils.imageSize(jsonObject.getString(PARAM_REVERSEIDCARD_ICCID))>5*1024*1024){
                throw new MyBusiException("图片大小应保持10KB-5M之间");
            }
            else if (CommonUtils.imageSize(jsonObject.getString(PARAM_BAREHEADPHOTO))<10*1024 || CommonUtils.imageSize(jsonObject.getString(PARAM_BAREHEADPHOTO))>5*1024*1024){
                throw new MyBusiException("图片大小应保持10KB-5M之间");
            }else if (jsonObject.getString(PARAM_CARDNUMBER).length()!=18) {
                throw new MyBusiException("身份证号码有误");
            } else if (validDatetakeeffect==false){
                throw  new  MyBusiException("日期格式不符合规范！");
            }else if (validDatefailure==false){
                throw  new  MyBusiException("日期格式不符合规范！");
            } else if (Integer.valueOf(format1)>Integer.valueOf(jsonObject.getString(PARAM_FAILUREOFCARDTIME)))
            {
                throw  new MyBusiException("证件已过期");
            } else if (IdCardVerification.IDCardValidate(jsonObject.getString(PARAM_CARDNUMBER))==false){
                throw  new  MyBusiException("证件号码有误！");
            }else if (jsonObject.getInteger(PARAM_ISPAS)==null ){
                throw  new  MyBusiException("参数不能为空");
            } else if (jsonObject.getInteger(PARAM_ISPAS)!=1 && jsonObject.getInteger(PARAM_ISPAS)!=0){
                throw  new  MyBusiException("参数非法");
            }else if (!jsonObject.getString(PARAM_PLATFORM).equals("9") && !jsonObject.getString(PARAM_PLATFORM).equals("10")){
                throw  new  MyBusiException("请输入正确的平台标识码");
            }
        }else {
            throw  new  MyBusiException("请输入正确的平台号");
        }

    }
}
