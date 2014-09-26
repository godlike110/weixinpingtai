package cn.focus.dc.focuswap.config;

public interface QuestionStatusConstants {

    
    /** 删除 */
    int DELETE = -1;
    
    /**问题未审核状态*/
    int QA_Q_UN_AUDIT = 1;
    
    /**问题未通过审核状态*/
    int QA_Q_UN_PASS_AUDIT = 2;
    
    
    /**问题审核通过未回答*/
    int QA_Q_PASS_NO_ANSWER = 3;
    
    /**回答未审核*/
    int QA_Q_PASS_AUDIT = 4;
    
    /**回答未通过审核*/
    int QA_A_UN_PASS_AUDIT = 5;
    
    /**审核通过(问题和回答都通过审核)*/
    int QA_PASS_AUDIT = 6;

}
