package konkuk.spostnet.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import konkuk.spostnet.abstractobject.Mail;
import konkuk.spostnet.core.Center;
import konkuk.spostnet.model.Employee;

public class ClassificationView implements View, java.util.Observer {

	private JPanel contentPane;
	private JFrame frame;
	private Employee employee;
	private List<Mail> mails = new ArrayList();
	private List<String> item = new ArrayList();
	private DefaultListModel listModel = new DefaultListModel();
	private JList list = new JList(listModel);
	JPanel panel;

	/**
	 * Launch the application.
	 */
	public ClassificationView() {
		frame = new JFrame();
		frame.setVisible(false);

	}

	/**
	 * Create the frame.
	 */
	public void showClassificationView() {

		mails = (List) Center.getCenter().getLmail();	// 3.1.1 mails := getLmail()

		for (int i = 0; i < mails.size(); i++) {		// 3.1.2 [*i=0...mail.size] mail:=get(i)
			if (mails.get(i).getStatus().equals("Registered")) {
				listModel.addElement(mails.get(i).getInvoiceNumber());	// 3.1.3 [mail.status==Registered] addElement(mail.invoiceNumber);
			}
		}
		list.setModel(listModel);
		if (item.size() == 0) {
			System.out.println("No Item in the list");
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(contentPane);

		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		final JButton btnClassifyButton = new JButton("Classify Item");
		btnClassifyButton.setBounds(224, 181, 130, 23);
		panel.add(btnClassifyButton);

		/*
		 * item = extractItem();
		 */

		// list = new JList(item.toArray());
		list.setBounds(23, 31, 171, 197);
		panel.add(list);
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(final ListSelectionEvent le) {
				btnClassifyButton.addActionListener(new ActionListener() { // Classify(invoiceNumber)
					public void actionPerformed(ActionEvent e) {
						if (e.getSource() instanceof JButton
								&& le.getValueIsAdjusting()) {

							System.out.println("Hello World Classification");
							int invoiceNumber = -1;
							if(list.getSelectedValue() != null){
								invoiceNumber = (Integer) list
									.getSelectedValue();
							System.out.println(invoiceNumber);
							
							List<Object> objects = new ArrayList();			// 1 create
							objects.add(invoiceNumber);						// 2 add(invoiceNumber)
							employee.getRole().doTask("Classify", objects);	// 3 role:=getRole(), 4 doTask("Classify", objects)
							}
						}
					}
				});

			}

		});

	}

	@Override
	public void setModel(Object model) {
		// TODO Auto-generated method stub
		Center.getCenter().addObserver(this);	//  2.1 addObserver(View)
		employee = (Employee) model;
	}

	@Override
	public void viewInvoker() {
		// TODO Auto-generated method stub
		showClassificationView(); 				// 3.1 show***View()
		frame.setVisible(true);
		;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		System.out.println("Update View!!!!");

		listModel.clear();

		mails = (List) Center.getCenter().getLmail();

		/*
		 * This should be Separate Method
		 */
		
		for (int i = 0; i < mails.size(); i++) {
			if (mails.get(i).getStatus().equals("Registered")) {
				listModel.addElement(mails.get(i).getInvoiceNumber());
			}
		}

		if (item.size() == 0) {
			System.out.println("No Item in the list");
		}
	}

}