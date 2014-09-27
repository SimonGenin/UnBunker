package be.simongenin.unbunker.classes;


import java.util.ArrayList;

public class History {

    public static final int TYPE_SALE = 1;
    public static final int TYPE_BUYING = 2;

    private int type;
    private User interactor;
    private String date;
    private int number;

    public History(int type, int interactorId, String date, int number) {
        this.type = type;
        this.date = date;
        this.number = number;

        User interactor = User.getUserById(interactorId);
        this.interactor = interactor;

    }

    public static  ArrayList<History> getSalesHistoryList(ArrayList<History> histories) {
        ArrayList<History> hl = new ArrayList<History>();
        for (History h : histories) {
            if (h.getType() == TYPE_SALE) {
                hl.add(h);
            }
        }

        return hl;
    }

    public static ArrayList<History> getBuyingHistoryList(ArrayList<History> histories) {
        ArrayList<History> hl = new ArrayList<History>();
        for (History h : histories) {
            if (h.getType() == TYPE_BUYING) {
                hl.add(h);
            }
        }

        return hl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public User getInteractor() {
        return interactor;
    }

    public void setInteractor(User interactor) {
        this.interactor = interactor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
