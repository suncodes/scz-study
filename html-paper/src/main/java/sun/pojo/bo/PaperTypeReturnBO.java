package sun.pojo.bo;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class PaperTypeReturnBO {

    /**试卷类型*/
//    private Integer resourceType;

    /**年级*/
    private String grade;

    /**年份*/
    private String year;

    /**学期*/
    private String term;

    /**版本*/
    private String version;

    /**类型*/
    private String period;

    /**省份*/
    private String province;

    /**地区*/
    private String area;

    /**学校*/
    private String org;

//    public Integer getResourceType() {
//        return resourceType;
//    }
//
//    public void setResourceType(Integer resourceType) {
//        this.resourceType = resourceType;
//    }


    public PaperTypeReturnBO() {
    }

    public PaperTypeReturnBO(String grade, String year, String term, String version, String period, String province,
                             String area, String org) {
        this.grade = grade;
        this.year = year;
        this.term = term;
        this.version = version;
        this.period = period;
        this.province = province;
        this.area = area;
        this.org = org;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getYear() { return year;}

    public void setYear(String year) {
        this.year = year;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) { this.province = province; }

    public String getArea() {
        return area;
    }

    public void setArea(String area) { this.area = area; }

    public String getOrg(){
        return org;
    }

    public void setOrg(String org){
        this.org = org;
    }
}

