package hu.ait.android.expensemanager.model;

import com.orm.SugarRecord;

public class Expense extends SugarRecord {
    private String desc;
    private Boolean isExpense;
    private float amount;

    public Expense() {
    }

    public Expense(String desc, float amount, Boolean isExpense) {
        this.amount = amount;
        this.isExpense = isExpense;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean getIsExpense() {
        return isExpense;
    }

    public void setIsExpense(Boolean isExpense) {
        this.isExpense = isExpense;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
