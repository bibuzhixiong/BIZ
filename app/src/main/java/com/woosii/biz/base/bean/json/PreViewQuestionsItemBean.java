package com.woosii.biz.base.bean.json;

/**
 * Created by Administrator on 2017/10/25.
 */

public class PreViewQuestionsItemBean {
    private String q_id;
    private String question;
    private String type;
    private String answer;
    private String parsing;
    private PreViewQuestionsSelectionBean questionoptions;

    public String getQ_id() {
        return q_id;
    }

    public void setQ_id(String q_id) {
        this.q_id = q_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getParsing() {
        return parsing;
    }

    public void setParsing(String parsing) {
        this.parsing = parsing;
    }

    public PreViewQuestionsSelectionBean getQuestionoptions() {
        return questionoptions;
    }

    public void setQuestionoptions(PreViewQuestionsSelectionBean questionoptions) {
        this.questionoptions = questionoptions;
    }

    @Override
    public String toString() {
        return "PreViewQuestionsItemBean{" +
                "q_id='" + q_id + '\'' +
                ", question='" + question + '\'' +
                ", type='" + type + '\'' +
                ", answer='" + answer + '\'' +
                ", parsing='" + parsing + '\'' +
                ", questionoptions=" + questionoptions +
                '}';
    }
}
