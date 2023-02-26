import javax.swing.*;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.*;

public class CriticalSectionSim extends JPanel{
	//just because
	private static final long serialVersionUID = -5590841217778366170L;
	public static final String PROB_FILENAME="problems";
	
	//Data
	private MutexProblem problem;
	private ArrayList<AbstractProcess> processes;
	
	//Thread pool
	private ExecutorService exec;
	
	//UI stuff
	private JComboBox<String> model;
	private JButton pause, reset;
	private SimPanel simPanel;
	private JTextArea logText;
	private JList<String> queueList;
	private JScrollPane logScroller;
	
	
	private CriticalSectionSim(){
		//data setup
		processes = new ArrayList<AbstractProcess>();
		problem = new MutexProblem();
		
		setupUI();
		setupActions();
		
		readProblems();
		
		//Thread pool set up
		exec = Executors.newCachedThreadPool();
	}
	
	private void setupUI(){
		setLayout(new BorderLayout());
		
		JPanel controls = new JPanel();
		
		model = new JComboBox<String>();
		pause = new JButton("Pause");
		reset = new JButton("Reset");
		
		controls.add(model);
		controls.add(pause);
		controls.add(reset);
		
		add(controls, BorderLayout.NORTH);
		
		
		JPanel display = new JPanel(new GridLayout(1, 2));
		simPanel = new SimPanel(processes);
		logText = new JTextArea();
		queueList = new JList<String>();
		
		
		JTabbedPane tabs  = new JTabbedPane();
		
		logScroller = new JScrollPane(logText);
		tabs.addTab("Log", logScroller);
		//tabs.addTab("Queue", new JScrollPane(queueList));
		
		display.add(simPanel);
		display.add(tabs);
		
		add(display, BorderLayout.CENTER);
	}
	
	private void setupActions(){
		//set up actions
		pause.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(AbstractProcess.isPaused()){
					AbstractProcess.resume();
					pause.setText("Pause");
				}
				else {
					AbstractProcess.pause();
					pause.setText("Resume");
					
				}
			}
		});
		
		reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//clearProcesses();
				resetSim();
			}
		});
		
		model.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				resetSim();
			}
		});
		
	}
	
	private void resetSim(){
		clearProcesses();
		String className = (String)model.getSelectedItem();
		try {
			problem = (MutexProblem)(Class.forName(className).newInstance());
			clearProcesses();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e1) {
			logMessage("main", "Unable to create class: " + className);
		}
	}
	
	private void clearProcesses(){
		for(AbstractProcess p: processes){
			p.setDone();
		}
		
		processes.clear();
	}
	
	private void readProblems(){
		//open the file "problems"
		try {
			Scanner in = new Scanner(new File(PROB_FILENAME));
			while(in.hasNext()){
				model.addItem(in.next());
			}
		}
		catch(FileNotFoundException e){
			//problem = new MutexProblem();
			logMessage("main", "No problem file found. Using defaults.");
			model.addItem("MutexProblem");
		}
		
	}
	
	
	public void addNewProcess(int type, int x, int y, int dx, int dy){
		//get the process from the problem
		AbstractProcess p = problem.createProcess(type);
		//set its location and direction
		p.setLocation(x, y);
		p.setDirection(dx, dy);
		
		//add it to the list of processes
		processes.add(p);
		
		//run it
		exec.execute(p);
	}

	
	public void logMessage(String sender, String msg){
		if(sender == null)
			sender = "NONE";
		logText.append(String.format("%s:\t%s\n", sender, msg));
		
	}
	
	public void logMessage(String msg){
		logMessage(null, msg);
	}
	
	public String getProblemText(){
		return problem.toString();
	}
	
	public static final int FRAME_WIDTH = 1000;
	public static final int FRAME_HEIGHT = 500;
	private static CriticalSectionSim sim;	
	
	public static CriticalSectionSim getInstance(){
		if(sim == null)
			sim = new CriticalSectionSim();
		return sim;
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run(){
				JFrame frame = new JFrame("Critical Section Simulation");

				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

				frame.setContentPane(getInstance());

				frame.setVisible(true);
			}
		});
		
	}

}
