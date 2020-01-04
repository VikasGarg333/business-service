package com.business.service.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Project.
 */
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String projectNo;

    private String projectName;

    private String projectStatus;

//    private Application application;
//
//    private InfoBeneficiaries infoBeneficiaries;
//
    private Commitment commitment;
//
//    private Assignment assignment;
//
//    private Member member;

    private Loanpool loanpool;

    private Set<Loan> loans = new HashSet<>();

    //    private Set<ProjectLog> projectLogs = new HashSet<>();
//
//    private Set<NotificationHistory> notificationHistories = new HashSet<>();
//
//    private Program program;
    private Set<DrawdownHistory> drawdownHistories = new HashSet<>();

    public Set<DrawdownHistory> getDrawdownHistories() {
        return drawdownHistories;
    }

    public Project drawdownHistories(Set<DrawdownHistory> drawdownHistories) {
        this.drawdownHistories = drawdownHistories;
        return this;
    }

    public Project addDrawdownHistory(DrawdownHistory drawdownHistory) {
        this.drawdownHistories.add(drawdownHistory);
        drawdownHistory.setProject(this);
        return this;
    }

    public Project removeDrawdownHistory(DrawdownHistory drawdownHistory) {
        this.drawdownHistories.remove(drawdownHistory);
        drawdownHistory.setProject(null);
        return this;
    }

    public void setDrawdownHistories(Set<DrawdownHistory> drawdownHistories) {
        this.drawdownHistories = drawdownHistories;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public Project projectNo(String projectNo) {
        this.projectNo = projectNo;
        return this;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public Project projectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public Project projectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
        return this;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

//    public Application getApplication() {
//        return application;
//    }
//
//    public Project application(Application application) {
//        this.application = application;
//        return this;
//    }
//
//    public void setApplication(Application application) {
//        this.application = application;
//    }
//
//    public InfoBeneficiaries getInfoBeneficiaries() {
//        return infoBeneficiaries;
//    }
//
//    public Project infoBeneficiaries(InfoBeneficiaries infoBeneficiaries) {
//        this.infoBeneficiaries = infoBeneficiaries;
//        return this;
//    }
//
//    public void setInfoBeneficiaries(InfoBeneficiaries infoBeneficiaries) {
//        this.infoBeneficiaries = infoBeneficiaries;
//    }
//
    public Commitment getCommitment() {
        return commitment;
    }

    public Project commitment(Commitment commitment) {
        this.commitment = commitment;
        return this;
    }

    public void setCommitment(Commitment commitment) {
        this.commitment = commitment;
    }
//
//    public Assignment getAssignment() {
//        return assignment;
//    }
//
//    public Project assignment(Assignment assignment) {
//        this.assignment = assignment;
//        return this;
//    }
//
//    public void setAssignment(Assignment assignment) {
//        this.assignment = assignment;
//    }
//
//    public Member getMember() {
//        return member;
//    }
//
//    public Project member(Member member) {
//        this.member = member;
//        return this;
//    }
//
//    public void setMember(Member member) {
//        this.member = member;
//    }

    public Loanpool getLoanpool() {
        return loanpool;
    }

    public Project loanpool(Loanpool loanpool) {
        this.loanpool = loanpool;
        return this;
    }

    public void setLoanpool(Loanpool loanpool) {
        this.loanpool = loanpool;
    }

    public Set<Loan> getLoans() {
        return loans;
    }

    public Project loans(Set<Loan> loans) {
        this.loans = loans;
        return this;
    }

    public Project addLoan(Loan loan) {
        this.loans.add(loan);
        loan.setProject(this);
        return this;
    }

    public Project removeLoan(Loan loan) {
        this.loans.remove(loan);
        loan.setProject(null);
        return this;
    }

    public void setLoans(Set<Loan> loans) {
        this.loans = loans;
    }

//    public Set<ProjectLog> getProjectLogs() {
//        return projectLogs;
//    }
//
//    public Project projectLogs(Set<ProjectLog> projectLogs) {
//        this.projectLogs = projectLogs;
//        return this;
//    }
//
//    public Project addProjectLog(ProjectLog projectLog) {
//        this.projectLogs.add(projectLog);
//        projectLog.setProject(this);
//        return this;
//    }
//
//    public Project removeProjectLog(ProjectLog projectLog) {
//        this.projectLogs.remove(projectLog);
//        projectLog.setProject(null);
//        return this;
//    }
//
//    public void setProjectLogs(Set<ProjectLog> projectLogs) {
//        this.projectLogs = projectLogs;
//    }
//
//    public Set<NotificationHistory> getNotificationHistories() {
//        return notificationHistories;
//    }
//
//    public Project notificationHistories(Set<NotificationHistory> notificationHistories) {
//        this.notificationHistories = notificationHistories;
//        return this;
//    }
//
//    public Project addNotificationHistory(NotificationHistory notificationHistory) {
//        this.notificationHistories.add(notificationHistory);
//        notificationHistory.setProject(this);
//        return this;
//    }
//
//    public Project removeNotificationHistory(NotificationHistory notificationHistory) {
//        this.notificationHistories.remove(notificationHistory);
//        notificationHistory.setProject(null);
//        return this;
//    }
//
//    public void setNotificationHistories(Set<NotificationHistory> notificationHistories) {
//        this.notificationHistories = notificationHistories;
//    }
//
//    public Program getProgram() {
//        return program;
//    }
//
//    public Project program(Program program) {
//        this.program = program;
//        return this;
//    }
//
//    public void setProgram(Program program) {
//        this.program = program;
//    }
//    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return id != null && id.equals(((Project) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", projectNo='" + getProjectNo() + "'" +
            ", projectName='" + getProjectName() + "'" +
            ", projectStatus='" + getProjectStatus() + "'" +
            "}";
    }
}
