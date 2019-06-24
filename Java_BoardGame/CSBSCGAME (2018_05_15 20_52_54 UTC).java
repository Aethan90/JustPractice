import javax.swing.JFrame;
package boardGame;
public class CSBSCGAME {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new gameBoard().setVisible(true);
            }
        });
    }
}
