package com.business.service.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.*;

import com.business.service.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

@Service
public class ProjectService {
	private final Logger log = LoggerFactory.getLogger(ProjectService.class);

	public List<Project> getProjects(){
        final String dataHubEndpointProjects = "http://mvp-dataservice.us-east-1.elasticbeanstalk.com:5000/services/dataservice/api/projects";
        final List<Project>  projects = new ArrayList<>();
        final RestTemplate restTemplate = new RestTemplate();

        /*Commenting this, in case we want to do in the right way*/
        /*ResponseEntity<Project[]> response = restTemplate.getForEntity(
            dataHubEndpointProjects,
            Project[].class);

        Project[] projectArray = response.getBody();
        for (final Project project: projectArray) {
            projects.add(project);
        }*/

        final String json = restTemplate.getForObject(
            dataHubEndpointProjects,
            String.class);

        final JsonArray jarr = new JsonParser().parse(json).getAsJsonArray();

        for (int i = 0; i < jarr.size(); i++) {
            final Project project = new Project();
            JsonElement id = jarr.get(i).getAsJsonObject().get("id");
            JsonElement projectNo = jarr.get(i).getAsJsonObject().get("projectNo");
            JsonElement projectName = jarr.get(i).getAsJsonObject().get("projectName");
            JsonElement program = jarr.get(i).getAsJsonObject().getAsJsonObject("program").get("programName");
            JsonElement memberName = jarr.get(i).getAsJsonObject().getAsJsonObject("member").get("memberName");
            JsonElement projectStatus = jarr.get(i).getAsJsonObject().get("projectStatus");
            JsonElement commitmentStatus = jarr.get(i).getAsJsonObject().getAsJsonObject("commitment").get("commitmentStatus");
            JsonElement commitmentExpiration = jarr.get(i).getAsJsonObject().getAsJsonObject("commitment").get("commitmentExpiration");

            project.setId(id instanceof JsonNull ? "" : id.getAsString());
            project.setProjectNo(projectNo instanceof JsonNull ? "" : projectNo.getAsString());
            project.setProjName(projectName instanceof JsonNull ? "" : projectName.getAsString());
            project.setProgram(program instanceof JsonNull ? "" : program.getAsString());
            project.setMember(memberName instanceof JsonNull ? "" : memberName.getAsString());
            project.setProjectStatus(projectStatus instanceof JsonNull ? "" : projectStatus.getAsString());
            project.setCommitmentStatus(commitmentStatus instanceof JsonNull ? "" : commitmentStatus.getAsString());
            String commitmentBal = jarr.get(i).getAsJsonObject().getAsJsonObject("commitment").get("commitmentBal").getAsString();
            project.setCommitmentBalance(Float.parseFloat(commitmentBal.isEmpty()? "0": commitmentBal));
            project.setCommitmentExpiration(commitmentExpiration instanceof JsonNull ? "" : commitmentExpiration.getAsString());
            projects.add(project);
        }

		return projects;
	}

	public List<Project> searchProject(String projectNo,
			String projectName,
			String program,
			String projectStatus,
			String commitmentStatus,
			String member)  {
        List<Project> results = new ArrayList<>();
        List<Project> projects = getProjects();
        for (Project project: projects) {
            if ((project.getProjectNo() != null && project.getProjectNo().equals(projectNo)) ||
                (project.getProjName() != null && projectName != null && (project.getProjName().contains(projectName))) ||
                (project.getProgram() != null && program != null && (project.getProgram().contains(program))) ||
                (project.getProjectStatus() != null && projectStatus != null && (project.getProjectStatus().contains(projectStatus))) ||
                (project.getCommitmentStatus() != null && commitmentStatus != null && (project.getCommitmentStatus().contains(commitmentStatus))) ||
                (project.getMember() != null && member != null && (project.getMember().contains(member)))) {
                results.add(project);
            }
        }

		return results;
	}

	public SearchControl getSearchControl() {
		SearchControl searchControl = new SearchControl();
		List<String> programsList = new ArrayList<String>();
		programsList.add("CIP");
		programsList.add("UDA");
		programsList.add("RDA");
		List<String> projectStatusList = new ArrayList<String>();
		projectStatusList.add("Approved");
		projectStatusList.add("Rejected");
		projectStatusList.add("In Review");
		List<String> commitmentStatusList = new ArrayList<String>();
		commitmentStatusList.add("Active");
		commitmentStatusList.add("Cancelled");
		commitmentStatusList.add("Expired");
		List<String> membersList = new ArrayList<String>();
		membersList.add("#1 - MEMBER 1");
		membersList.add("#2 - MEMBER 2");
		membersList.add("#3 - MEMBER 3");

		searchControl.setCommitmentStatusList(commitmentStatusList);
		searchControl.setMembersList(membersList);
		searchControl.setProgramsList(programsList);
		searchControl.setProjectStatusList(projectStatusList);

		return searchControl;
	}

	public ProjectDetails getProjectInfoBeneficiaries(String projectNo) {
	    final Project project = getProjectDetails(projectNo);
        final ProjectDetails projectDetails = new ProjectDetails();
        final String dataHubEndpointProjectInfoBeneficiaries = "http://mvp-dataservice.us-east-1.elasticbeanstalk.com:5000/services/dataservice/api/info-beneficiaries/" + project.getId();
        final RestTemplate restTemplate = new RestTemplate();

        final String json = restTemplate.getForObject(
            dataHubEndpointProjectInfoBeneficiaries,
            String.class);

        final JsonObject jObj = new JsonParser().parse(json).getAsJsonObject();

        JsonElement jobCreated = jObj.get("jobCreated");
        JsonElement jobRetained = jObj.get("jobRetained");
        JsonElement ownerOccUnits = jObj.get("ownerOccUnits");
        JsonElement rentalUnits = jObj.get("rentalUnits");
        JsonElement geoDefinedBeneficiaries = jObj.get("geoDefinedBeneficiaries");
        JsonElement individualBeneficiaries = jObj.get("individualBeneficiaries");
        JsonElement activityBeneficiaries = jObj.get("activityBeneficiaries");
        JsonElement otherBeneficiaries = jObj.get("otherBeneficiaries");
        JsonElement area = jObj.get("area");
        JsonElement developmentInd = jObj.get("developmentInd");


		projectDetails.setProjectNo(projectNo);
		projectDetails.setProjName(project.getProjName());
		projectDetails.setProgramType(area instanceof JsonNull ? "" : area.getAsString());
		projectDetails.setProjectType(developmentInd instanceof JsonNull ? "" : developmentInd.getAsString());
		projectDetails.setNoOfRentalUnits(rentalUnits instanceof JsonNull ? 0 : rentalUnits.getAsInt());
		projectDetails.setNoOfOwnedUnits(ownerOccUnits instanceof JsonNull ? 0 : ownerOccUnits.getAsInt());
		projectDetails.setNoOfJobsCreated(jobCreated instanceof JsonNull ? 0 : jobCreated.getAsInt());
		projectDetails.setNoOfJobsRetained(jobRetained instanceof JsonNull ? 0 : jobRetained.getAsInt());
		projectDetails.setGeoDefinedBeneficiaries(geoDefinedBeneficiaries instanceof JsonNull ? "" : geoDefinedBeneficiaries.getAsString());
		projectDetails.setIndividualBeneficiaries(individualBeneficiaries instanceof JsonNull ? "" : individualBeneficiaries.getAsString());
		projectDetails.setActivityBeneficiaries(activityBeneficiaries instanceof JsonNull ? "": activityBeneficiaries.getAsString());
		projectDetails.setOtherBeneficiaries(otherBeneficiaries instanceof JsonNull ? "" : otherBeneficiaries.getAsString());

		return projectDetails;
	}

	public ApplicationReviewDetails getApplicationReviewDetails(String projectNo) {
        final Project project = getProjectDetails(projectNo);
        final ApplicationReviewDetails applicationReviewDetails = new ApplicationReviewDetails();
        final String dataHubEndpointProjectApplicationReviewDetails = "http://mvp-dataservice.us-east-1.elasticbeanstalk.com:5000/services/dataservice/api/applications/" + project.getId();
        final RestTemplate restTemplate = new RestTemplate();

        final String json = restTemplate.getForObject(
            dataHubEndpointProjectApplicationReviewDetails,
            String.class);

        final JsonObject jObj = new JsonParser().parse(json).getAsJsonObject();

        JsonElement applicationDate = jObj.get("applicationDate");
        JsonElement totalAmountRequested = jObj.get("totalAmountRequested");
        JsonElement certificationName = jObj.get("certificationName");
        JsonElement certificationTitle = jObj.get("certificationTitle");
        JsonElement certificationDate = jObj.get("certificationDate");
        JsonElement projectSpecificApplication = jObj.get("projectSpecificApplication");
        JsonElement currentReviewStatus = jObj.get("currentReviewStatus");
        JsonElement currentAssign = jObj.getAsJsonObject("assignment").get("currentAssign");
        JsonElement currentAssStDate = jObj.getAsJsonObject("assignment").get("currentAssStDate");
        JsonElement analystAssign = jObj.getAsJsonObject("assignment").get("analystAssign");
        JsonElement managerAssign = jObj.getAsJsonObject("assignment").get("managerAssign");
        JsonElement reviewStartDate = jObj.getAsJsonObject("assignment").getAsJsonObject("worker").getAsJsonObject("review").get("reviewStartDate");

		applicationReviewDetails.setApplicationDate(applicationDate instanceof JsonNull ? "" : applicationDate.getAsString());
		applicationReviewDetails.setTotalAmountRequested(totalAmountRequested instanceof JsonNull ? 0 : totalAmountRequested.getAsDouble());
		applicationReviewDetails.setCertifiationName(certificationName instanceof JsonNull ? "" : certificationName.getAsString());
		applicationReviewDetails.setCertificationTitle(certificationTitle instanceof JsonNull ? "" : certificationTitle.getAsString());
		applicationReviewDetails.setCertificationDate(certificationDate instanceof JsonNull ? "" : certificationDate.getAsString());
		applicationReviewDetails.setProjectSpecificApplication(projectSpecificApplication instanceof JsonNull ? false : ("NO".equals(projectSpecificApplication.getAsString()) ? false : true));
		applicationReviewDetails.setCurrentReviewStatus(currentReviewStatus instanceof JsonNull ? "" : currentReviewStatus.getAsString());
		applicationReviewDetails.setCurrentAssignment(currentAssign instanceof JsonNull ? "" : currentAssign.getAsString());
		applicationReviewDetails.setCurrentAssignmentStartDate(currentAssStDate instanceof JsonNull ? "" : currentAssStDate.getAsString());
		applicationReviewDetails.setCurrentTurntimeDaysElapsed(6);
		applicationReviewDetails.setAssignedAnalyst(analystAssign instanceof JsonNull ? "" : analystAssign.getAsString());
		applicationReviewDetails.setAnalystReviewStartDate(reviewStartDate instanceof JsonNull ? "" : reviewStartDate.getAsString());
		applicationReviewDetails.setAssinedManager(managerAssign instanceof JsonNull ? "" : managerAssign.getAsString());

		return applicationReviewDetails;
	}

	public EmailNotificationsAndContacts getEmailNotificationsAndContacts(String projectNo) {
        final Project project = getProjectDetails(projectNo);
        EmailNotificationsAndContacts emailNotificationsAndContacts = new EmailNotificationsAndContacts();
        final String dataHubEndpointProjectNotificationHistories = "http://mvp-dataservice.us-east-1.elasticbeanstalk.com:5000/services/dataservice/api/notification-histories/" + project.getId();
        final RestTemplate restTemplate = new RestTemplate();

        final String json = restTemplate.getForObject(
            dataHubEndpointProjectNotificationHistories,
            String.class);

        final JsonObject jObj = new JsonParser().parse(json).getAsJsonObject();

        JsonElement sentDate = jObj.get("sentDate");
        JsonElement fromEmail = jObj.get("fromEmail");
        JsonElement toAddress = jObj.get("toAddress");
        JsonElement ccAddress = jObj.get("ccAddress");
        JsonElement notificationType = jObj.get("notificationType");
        JsonElement subjectLine = jObj.get("subjectLine");

		List<EmailNotification> emailNotificationsList = new ArrayList<EmailNotification>();
		EmailNotification emailNotification1 = new EmailNotification();
		emailNotification1.setSentDate(sentDate instanceof JsonNull ? "" : sentDate.getAsString());
		emailNotification1.setFromEmailAddress(fromEmail instanceof JsonNull ? "" : fromEmail.getAsString());
        List<String> toEmailAddresses = new ArrayList<>();
        String toAddresses[]= toAddress.getAsString().split(",");
        for (String toEmail : toAddresses) {
            toEmailAddresses.add(toEmail);
        }
		emailNotification1.setToEmailAddress(toEmailAddresses);

		List<String> ccEmailAddresses = new ArrayList<>();
        String ccAddresses[]= ccAddress.getAsString().split(",");
        for (String ccEmail : ccAddresses) {
            ccEmailAddresses.add(ccEmail);
        }
		emailNotification1.setCcEmailAddress(ccEmailAddresses);
		emailNotification1.setNotificationType(notificationType instanceof JsonNull ? "" : notificationType.getAsString());
		emailNotification1.setSubjectLine(subjectLine instanceof JsonNull ? "" : subjectLine.getAsString());
		emailNotificationsList.add(emailNotification1);

		emailNotificationsAndContacts.setEmailNotificationsList(emailNotificationsList);

		List<ProjectContacts> projectContactsList = new ArrayList<ProjectContacts>();

		ProjectContacts projectContacts1 = new ProjectContacts();
		projectContacts1.setContactName("John Smith");
		projectContacts1.setTitle("Lending Specialist");
		projectContacts1.setSource("Application (Certification Section)");
		projectContacts1.setPhoneNumber("(212) 960-8841");
		projectContacts1.setEmailAddress("sohn.smith@memberbank.com");

		projectContactsList.add(projectContacts1);

		ProjectContacts projectContacts2 = new ProjectContacts();
		projectContacts2.setContactName("Jane Anderson");
		projectContacts2.setTitle("Lending Analyst");
		projectContacts2.setSource("CRM");
		projectContacts2.setPhoneNumber("(212) 960-8841");
		projectContacts2.setEmailAddress("jane.anderson@memberbank.com");

		projectContactsList.add(projectContacts2);

		emailNotificationsAndContacts.setProjectContactsList(projectContactsList);

		return emailNotificationsAndContacts;
	}

	public List<ProjectLog> getProjectLog(String projectNo){
        final Project project = getProjectDetails(projectNo);
        List<ProjectLog> projectLogList = new ArrayList<>();
        final String dataHubEndpointProjectLogs = "http://mvp-dataservice.us-east-1.elasticbeanstalk.com:5000/services/dataservice/api/project-logs/" + project.getId();
        final RestTemplate restTemplate = new RestTemplate();

        final String json = restTemplate.getForObject(
            dataHubEndpointProjectLogs,
            String.class);

        final JsonObject jObj = new JsonParser().parse(json).getAsJsonObject();

        JsonElement worker = jObj.get("worker");
        JsonElement entryDetails = jObj.get("entryDetails");
        JsonElement date = jObj.get("date");

        ProjectLog projectLog = new ProjectLog();
		projectLog.setProjectDate(date instanceof JsonNull ? "" : date.getAsString());
		projectLog.setEntryDetails(entryDetails instanceof JsonNull ? "" : entryDetails.getAsString());
		projectLog.setProjectUser(worker instanceof JsonNull ? "" : worker.getAsString());
		projectLogList.add(projectLog);

		return projectLogList;
	}

    private Project getProjectDetails(String projectNumber) {
        //final String dataHubEndpointProjects = "http://mvp-dataservice.us-east-1.elasticbeanstalk.com:5000/services/dataservice/api/projectsbyprojectid/" + projectNumber;
        final String dataHubEndpointProjects = "http://mvp-dataservice.us-east-1.elasticbeanstalk.com:5000/services/dataservice/api/projects/1";
        final RestTemplate restTemplate = new RestTemplate();
        final Project project = new Project();

        final String json = restTemplate.getForObject(
            dataHubEndpointProjects,
            String.class);

        final JsonObject jObj = new JsonParser().parse(json).getAsJsonObject();

        JsonElement projectName = jObj.get("projectName");
        JsonElement idJSONElement = jObj.get("id");

        project.setId(idJSONElement instanceof JsonNull ? "" : idJSONElement.getAsString());
        project.setProjectNo(projectNumber);
        project.setProjName(projectName instanceof JsonNull ? "" : projectName.getAsString());

        return project;
    }

}
