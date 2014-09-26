package cn.focus.dc.focuswap.json.filter;

import cn.focus.dc.focuswap.model.QuestionInfoExt;

import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

public class QuestionInfoExtJsonFilter extends SimplePropertyPreFilter {

    private static QuestionInfoExtJsonFilter instance = new QuestionInfoExtJsonFilter();
    
    private QuestionInfoExtJsonFilter() {
        super(QuestionInfoExt.class, "userHeadPic", "userName", "questionTimeStr", "question", "editorHeadPic", "editorName", "answerTimeStr", "answer", "groupId", "buildName", "questionId", "status");
    }
    
    public static QuestionInfoExtJsonFilter getInstance() {
        return instance;
    }
}
