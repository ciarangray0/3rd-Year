public enum Input {
    REGISTER_USER(1),
    DELETE_USER(2),
    MAKE_POST(3),
    VIEW_FORUM(4),
    EXIT(5)
    ;

    public int optionNum;
     Input(int optionNum) {
        this.optionNum = optionNum;
    }
    public static Input userChoice(int choice) {
         for(Input inputs : Input.values()) {
             if (inputs.optionNum == choice) {
                 return inputs;
             }
         }
         return null;
    }
}
