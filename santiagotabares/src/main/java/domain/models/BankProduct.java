package domain.models;





public abstract class BankProduct {

    
    private String code;
    private String name;
    private boolean requiresApproval;

    public boolean requiresApproval() {
        return requiresApproval;
    }

    public String getCode() {
        return code;
    }




}
