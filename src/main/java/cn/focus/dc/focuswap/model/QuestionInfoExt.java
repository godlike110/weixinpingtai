package cn.focus.dc.focuswap.model;

import java.util.Date;

public class QuestionInfoExt extends QuestionInfo{

    private static final long serialVersionUID = 4890023440452046586L;

    private String editorHeadPic;
    
    private String userHeadPic;
    
    private Date answerTime;
    
    //回答时间，字符串形式，方便前端展示
    private String answerTimeStr;
    
    //提问时间，字符串形式，方便前段展示
    private String questionTimeStr;

    public String getEditorHeadPic() {
        return editorHeadPic;
    }

    public void setEditorHeadPic(String editorHeadPic) {
        this.editorHeadPic = editorHeadPic;
    }

    public String getUserHeadPic() {
        return userHeadPic;
    }

    public void setUserHeadPic(String userHeadPic) {
        this.userHeadPic = userHeadPic;
    }

    public Date getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
    }

    public String getAnswerTimeStr() {
        return answerTimeStr;
    }

    public void setAnswerTimeStr(String answerTimeStr) {
        this.answerTimeStr = answerTimeStr;
    }

    public String getQuestionTimeStr() {
        return questionTimeStr;
    }

    public void setQuestionTimeStr(String questionTimeStr) {
        this.questionTimeStr = questionTimeStr;
    }

    
    
}
