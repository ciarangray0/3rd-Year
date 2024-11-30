import javax.print.attribute.standard.DialogTypeSelection;

public enum Footballers {
    HAALAND(96, 58, 84, 75),
    MESSI(88, 49, 96, 95),
    RONALDO(95, 60, 92, 83),
    DIAS(55, 96, 72, 78),
    KANTE(66, 92, 87, 82),
    RODRI(88, 92, 81, 90)
    ;
    private final int shooting;
    private final int defending;
    private final int passing;
    private final int dribbling;

    Footballers(int shooting, int defending, int dribbling, int passing) {
        this.shooting = shooting;
        this.defending = defending;
        this.dribbling = dribbling;
        this.passing = passing;
    }
    public int shooting() {
        return this.shooting;
    }
    public int defending() {
        return this.defending;
    }
    public int passing() {
        return this.passing;
    }
    public int dribbling() {
        return this.dribbling;
    }

    public static Footballers compareCards(Attribute category, Footballers ... footballer) {
        Footballers result = footballer[0];
        switch(category) {
            case SHOOTING:
                for(Footballers f : footballer) {
                    if (f.shooting() > result.shooting()) {
                        result = f;
                    }
                }
                return result;
            case DRIBBLING:
                for(Footballers f : footballer) {
                    if (f.dribbling() > result.dribbling()) {
                        result = f;
                    }
                }
                return result;
            case DEFENDING:
                for(Footballers f : footballer) {
                    if (f.defending() > result.defending()) {
                        result = f;
                    }
                }
                return result;
            case PASSING:
                for(Footballers f : footballer) {
                    if (f.passing() > result.passing()) {
                        result = f;
                    }
                }
                return result;
        }
        return result;
    }
    enum Attribute {
        SHOOTING, DEFENDING, DRIBBLING, PASSING;
    }
    public static void main(String args[]) {
        System.out.println(compareCards(Attribute.PASSING, Footballers.HAALAND, Footballers.RODRI));
    }
}
