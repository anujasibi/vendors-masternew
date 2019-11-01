package creo.com.vendors;

public class EarningPojo {

    public String name;
    public String date;
    public String amount;
    public String process;

    public EarningPojo() {
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.process=process;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }
}
