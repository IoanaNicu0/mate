package org.fidami.mate;

public class Operation {
    private Terms terms;
    private OperationEnum operationEnum;
    private String operationText;
    private Long result;
    private static final String CALCULATION_TEMPLATE = "%d %s %d = ";

    Operation(Terms terms, OperationEnum operationEnum) {
        this.terms = terms;
        this.operationEnum = operationEnum;
        this.result = null;
    }

    public Operation() {

    }

    public OperationEnum getOperation() {
        return operationEnum;
    }

    public void setOperation(OperationEnum operationEnum) {
        this.operationEnum = operationEnum;
    }

    public Long getResult() {
        return result;
    }

    public void setResult(Long result) {
        this.result = result;
    }

    public Terms getTerms() {
        return terms;
    }

    public void setTerms(Terms terms) {
        this.terms = terms;
    }

    public String getOperationText() {
        return operationText;
    }

    public void setOperationText(String operationText) {
        this.operationText = operationText;
    }

    public OperationEnum getOperationEnum() {
        return operationEnum;
    }

    public void setOperationEnum(OperationEnum operationEnum) {
        this.operationEnum = operationEnum;
    }

    public String print() {
        return String.format(CALCULATION_TEMPLATE, terms.a(), operationEnum, terms.b());
    }

}
