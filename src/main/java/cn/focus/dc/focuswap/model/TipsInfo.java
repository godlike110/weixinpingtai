package cn.focus.dc.focuswap.model;

import cn.focus.dc.commons.model.BaseObject;

/**
 * 爬房团小贴士
 * @author rogantian
 * @date 2013-12-19
 * @email rogantianwz@gmail.com
 */
public class TipsInfo extends BaseObject {

    /**
     * 
     */
    private static final long serialVersionUID = 6314581560474095280L;

    private Integer qa_id;

    private String question_content;

    private String answer_content;

    private Integer create_login_id;

    private String create_login_name;

    private Integer create_time;

    private Integer status;

    private Integer city_id;

    private Integer count;

    public Integer getQa_id() {
        return qa_id;
    }

    public void setQa_id(Integer qa_id) {
        this.qa_id = qa_id;
    }

    public String getQuestion_content() {
        return question_content;
    }

    public void setQuestion_content(String question_content) {
        this.question_content = question_content;
    }

    public String getAnswer_content() {
        return answer_content;
    }

    public void setAnswer_content(String answer_content) {
        this.answer_content = answer_content;
    }

    public Integer getCreate_login_id() {
        return create_login_id;
    }

    public void setCreate_login_id(Integer create_login_id) {
        this.create_login_id = create_login_id;
    }

    public String getCreate_login_name() {
        return create_login_name;
    }

    public void setCreate_login_name(String create_login_name) {
        this.create_login_name = create_login_name;
    }

    public Integer getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Integer create_time) {
        this.create_time = create_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    
    
}
