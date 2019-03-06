package enumerated;

public enum OzWitch {
    EAST("fdsafsdafsa fdafa"),NORTH("nnnnnnnnnnhhhhhhhmmmmm"),SOUTH("sssssssss"),WEST("wwwwwww");
    private String des;
    private OzWitch(String des){
        this.des = des;
    }
    public String getDes(){
        return des;
    }

    public static void main(String[] args) {
        for (OzWitch witch : OzWitch.values()){
            System.out.println(witch + " : " + witch.getDes());
        }
    }
}
