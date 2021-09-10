import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class JavaCrud {

    private JPanel panel1;
    private JTextField imie;
    private JTextField plec;
    private JTextField wiek;
    private JButton addbuton;
    private JList list1;
    private JButton skasujButton;
    private JLabel average;
    private JLabel maxage;
    private JLabel minage;

    Connection con;
    PreparedStatement pst;


    public static void main(String[] args) {
        JFrame frame=new JFrame("User App");
        frame.setContentPane(new JavaCrud().panel1);
        frame.setSize(800,500);
        frame.setVisible(true);
    }

    public JavaCrud(){
        table_update();


        addbuton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name,sex,age;
                name = imie.getText();
                sex = plec.getText();
                age = wiek.getText();

if(imie.getText().isEmpty()||plec.getText().isEmpty()||wiek.getText().isEmpty()){
    JOptionPane.showMessageDialog(null,"Pola nie moga byc puste");
}
else {

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost/javasql", "root", "");
        pst = con.prepareStatement("Insert into user(name,age,sex)values(?,?,?)");
        pst.setString(1, name);
        pst.setString(2, age);
        pst.setString(3, sex);
        pst.execute();
        table_update();
        JOptionPane.showMessageDialog(null, "Dodano rekord");

    } catch (SQLException | ClassNotFoundException ex) {
        ex.printStackTrace();
    }
}
            }
        });
        list1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }
        });
        skasujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String whatTheUserEntered = JOptionPane.showInputDialog("Podaj id które chcesz sksowac");
                  //  System.out.println(whatTheUserEntered);

                try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                con=DriverManager.getConnection("jdbc:mysql://localhost/javasql","root","");
                pst=con.prepareStatement("delete from user where uid=?");

                pst.setString(1,whatTheUserEntered);
                pst.executeUpdate();
                table_update();

                }
                catch(SQLException | ClassNotFoundException ex){
                    ex.printStackTrace();
                }


            }
        });
        imie.addMouseListener(new MouseAdapter() {

        });
    }





    private void table_update() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost/javasql","root","");
            pst=con.prepareStatement("Select *from user");

            ResultSet rs=pst.executeQuery();

            Vector v2 = new Vector();
            List userage=new ArrayList();

            if(rs.next()){
                do{
                        v2.add(" Id: "+rs.getString("uid"));
                        v2.add(" Imie: "+rs.getString("name"));
                        v2.add(" Płeć: "+rs.getString("sex"));
                        v2.add(" Wiek: "+rs.getString("age"));
                        v2.add(" ---");

                        userage.add(rs.getInt("age"));

                        //
                  //  System.out.println(rs.getInt("age"));

                  list1.setListData(v2);


                }while(rs.next());



                int wynik=0;
                int sred;
                String pomoc;
                int max;
               for(int i=0;i<userage.size();i++) {
               pomoc=userage.get(i).toString();
               wynik+=Integer.parseInt(pomoc);


               }
               //
                sred=wynik/userage.size();
                average.setText(String.valueOf(sred));
               // System.out.println(wynik);
              //  System.out.println(Collections.max(userage)+"DS");
                maxage.setText(String.valueOf(Collections.max(userage)));
                minage.setText(String.valueOf(Collections.min(userage)));


            }
            else{
                System.out.println("Nie znaleziono");
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e);
        }



    }


}
