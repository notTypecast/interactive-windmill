package line;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Menu extends JPanel implements ActionListener {

	private static final long serialVersionUID = -3382409826153767254L;
	
	protected JTextField textField;
    protected JTextPane textPane;
    protected JComboBox<String> ddmenu;
    private static Data data;

    public Menu(Data dt) {
        super(new GridBagLayout());
        
        data = dt;        
        Font font = new Font("Jokerman", Font.PLAIN, 25);
        JLabel tp_label = new JLabel("TOTAL POINTS");
        tp_label.setFont(font);

        textField = new JTextField(10);
        textField.addActionListener(this);
        textField.setHorizontalAlignment(JTextField.CENTER);
        
        textPane = new JTextPane();
        textPane.setContentType("text/html");
        if (data.recommended_points != 0)
        	textPane.setText("<html>Too many points.<br>Try max " + String.valueOf(data.recommended_points) + ".</html>");
        else
        	textPane.setText("<html><body style = 'width: 120 px'></body></html>");
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        
        JLabel pl_label = new JLabel("POINT LIMITATIONS");
        pl_label.setFont(font);
        
        String[] choices = {"Yes", "No"};
        
        ddmenu = new JComboBox<String>(choices);
        ddmenu.setVisible(true);
        ddmenu.setToolTipText("<html>Limitations:<br>->No 3 points should be collinear<br>->Points shouldn't be too close<br>Limitations ensure that the line will pass from every point</html>");
        ddmenu.addActionListener(this);

        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.CENTER;
        
        add(tp_label, c);
        add(textField, c);
        add(textPane, c);
        add(pl_label, c);
        add(ddmenu, c);
    }

    public void actionPerformed(ActionEvent evt) {
        String text = textField.getText();
        data.limitations = (ddmenu.getSelectedItem() == "Yes") ? true : false;
        
        try {
        	int total_points = Integer.parseInt(text);
        	if (total_points <= 0)
        		throw new Exception();
        	data.total_points = total_points;
        	data.simulating = true;
        }
        catch (Exception e) {
        	textField.selectAll();
        	textPane.setText("<html><body style = 'width: 200;'>Expected a natural number.</body></html>");
        }
        

    }
}
