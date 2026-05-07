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
    public String getName() { return name; }

    public static BankProduct reconstitute(String code, String name, boolean requiresApproval) {
        BankProduct product = new BankProduct() {};
        product.code = code;
        product.name = name;
        product.requiresApproval = requiresApproval;
        return product;
    }
}