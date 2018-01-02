package com.huanggang.sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目基本信息实体类
 *
 * @author huangg
 */
public class Project implements Serializable {
    private static final long serialVersionUID = -208219152823511997L;
    private String projectId;// 项目id
    private String projectName;// 项目名
    private String leader;// 负责人
    private int siteCount;// 站点数
    private int siteExplored;// 已勘站点数
    private int gsmCount;// GSM总数
    private int g4Count;// 4G总数
    private int gsmExplored;// GSM已经勘点数
    private int g4Explored;// 4G已经勘点数
    private int progress;// 项目进度
    private int status;// 项目状态
    private String contacter;// 接口人
    private String contact;// 接口人电话
    private String province;// 省
    private String city;// 市
    private String createTime;// 创建时间
    private String updateTime;// 最后修改时间(对应最新站点时间)
    private int isCare = -1;// 0:关注，1：不关注
    private int siteBuilded;// 站点已建数
    private int siteListCount;// 站点列表数

    /**
     * 一条项目测试数据
     *
     * @author huangg
     */
    public static Project projectData(int i) {
        Project project = new Project();
        project.setProjectId("10086");
        project.setProjectName("广州市局天河区项目" + i);
        project.setProgress(i);
        project.setSiteExplored(i);
        project.setLeader("陈平" + i);
        project.setUpdateTime("2016/06/" + i);
        project.setSiteCount(100);
        project.setGsmCount(50);
        project.setG4Count(50);
        project.setGsmExplored(20);
        project.setG4Explored(10);
        project.setStatus(1);
        project.setContacter("张晓风");
        project.setContact("18512345678");
        project.setProvince("广东省");
        project.setCity("广州市");
        project.setCreateTime("2016/06/01");
        project.setIsCare(1);
        return project;
    }

    /**
     * 项目列表测试数据
     *
     * @param index 项目列表条数
     * @return
     * @author huangg
     */
    public static List<Project> projectDatas(int index) {
        List<Project> datas = new ArrayList<Project>();
        for (int i = 1; i <= index; i++) {
            Project project = projectData(i);
            datas.add(project);
        }
        return datas;
    }

    @Override
    public String toString() {
        return "Project [projectId=" + projectId + ", projectName=" + projectName + ", leader=" + leader + ", siteCount="
                + siteCount + ", siteExplored=" + siteExplored + ", gsmCount=" + gsmCount + ", g4Count=" + g4Count
                + ", gsmExplored=" + gsmExplored + ", g4Explored=" + g4Explored + ", progress="
                + progress + ", status=" + status + ", contacter=" + contacter + ", contact=" + contact + ", province="
                + province + ", city=" + city + ", createTime=" + createTime + ", updateTime=" + updateTime
                + ", isCare=" + isCare + ", siteBuilded=" + siteBuilded + ", siteListCount=" + siteListCount + "]";
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public int getSiteCount() {
        return siteCount;
    }

    public void setSiteCount(int siteCount) {
        this.siteCount = siteCount;
    }

    public int getSiteExplored() {
        return siteExplored;
    }

    public void setSiteExplored(int siteExplored) {
        this.siteExplored = siteExplored;
    }

    public int getGsmCount() {
        return gsmCount;
    }

    public void setGsmCount(int gsmCount) {
        this.gsmCount = gsmCount;
    }

    public int getG4Count() {
        return g4Count;
    }

    public void setG4Count(int g4Count) {
        this.g4Count = g4Count;
    }

    public int getGsmExplored() {
        return gsmExplored;
    }

    public void setGsmExplored(int gsmExplored) {
        this.gsmExplored = gsmExplored;
    }

    public int getG4Explored() {
        return g4Explored;
    }

    public void setG4Explored(int g4Explored) {
        this.g4Explored = g4Explored;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContacter() {
        return contacter;
    }

    public void setContacter(String contacter) {
        this.contacter = contacter;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getIsCare() {
        return isCare;
    }

    public void setIsCare(int isCare) {
        this.isCare = isCare;
    }

    public int getSiteBuilded() {
        return siteBuilded;
    }

    public void setSiteBuilded(int siteBuilded) {
        this.siteBuilded = siteBuilded;
    }

    public int getSiteListCount() {
        return siteListCount;
    }

    public void setSiteListCount(int siteListCount) {
        this.siteListCount = siteListCount;
    }

}
