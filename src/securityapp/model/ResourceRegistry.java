package securityapp.model;

public class ResourceRegistry {

	public static final Resource COMPANY_WEBSITE = new Resource("Company Website", AccessScope.PUBLIC);
	public static final Resource DEV_REPO = new Resource("Development Repository", AccessScope.INTERNAL);
	public static final Resource PROJECT_DOCS = new Resource("Project Documents", AccessScope.INTERNAL);
	public static final Resource PAYROLL_DB = new Resource("Payroll Database", AccessScope.CONFIDENTIAL);
	public static final Resource HR_RECORDS = new Resource("HR Records", AccessScope.CONFIDENTIAL);
	
}
