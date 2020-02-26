import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class WCSTgui extends JFrame{

	public String outputFileName = "userlog.txt";


	private JPanel panel,optionPanel,questionPanel,errorPanel;
	private List<String> options;
	private List<Card> selectOptions= new ArrayList<Card>();
	private List<Card> cardList = new ArrayList<Card>();
	public String question = "";
	private int counter = 0;
	private int rule ;

	int nmaxgames = 6; // by Z, initial value is 6
	int nmaxgamesprev = 6; // by Z, initial value is 6


	//private List<String> finalResult = new ArrayList<String>();
	private List<String> resultOfThisRound = new ArrayList<String>();
	private List<JButton> jButtonList = new ArrayList<JButton>();
	private Long startTime,endTime;

	public WCSTgui(){
		getContentPane().setPreferredSize( Toolkit.getDefaultToolkit().getScreenSize());
		//setDefaultCloseOperation(EXIT_ON_CLOSE);

		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		this.add(panel);

		optionPanel = new JPanel();
		optionPanel.setLayout(new FlowLayout(FlowLayout.CENTER,100,100));
		questionPanel = new JPanel();
		errorPanel = new JPanel();

		panel.add(optionPanel);
		panel.add(errorPanel);
		panel.add(questionPanel);

		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				//writeLogFile(finalResult);
				appendLogFile(resultOfThisRound); // write the output of this round as soon as it is produced
				System.exit(0);
			}
		});
		init();
	}
	public void init(){	
		String path = "src/image";
		File dir = new File(path);
		File[] listOfFiles = dir.listFiles();
		if(dir.isDirectory()){
			for(int i=0;i<listOfFiles.length;i++){
				String str = listOfFiles[i].getName();
				Card card = new Card(str,path+"/"+str);
				cardList.add(card);
			}

		}
		rule = randomRule();

		System.out.println("rule "+rule);
		getOption();
	}
	public void getOption(){
		options = new ArrayList<String>();
		selectOptions= new ArrayList<Card>();
		options = randomOption();
		for(int i=0;i<options.size();i++){
			for(int j=0;j<cardList.size();j++){
				if(options.get(i).equals(cardList.get(j).getName())){
					selectOptions.add(cardList.get(j));
				}
			}
		}
		this.addOption(selectOptions);
		question = addQuestion(selectOptions);
		//startTime = new Timestamp(System.currentTimeMillis());
		//System.out.println("start"+startTime);
	}
	private List<String> randomOption(){
		List<String> result = new ArrayList<String>();
		List<String> shape = new ArrayList<String>();
		List<String> color = new ArrayList<String>();
		List<String> num = new ArrayList<String>();
		for(int i=1;i<=4;i++){
			shape.add(Integer.toString(i));
			color.add(Integer.toString(i));
			num.add(Integer.toString(i));
		}
		Collections.shuffle(shape);
		Collections.shuffle(color);
		Collections.shuffle(num);

		for(int i=0;i<shape.size();i++){
			result.add(shape.get(i)+color.get(i)+num.get(i));

		}
		return result;
	}
	private void addOption(List<Card> options){
		startTime = System.currentTimeMillis();
		System.out.println("start "+startTime);
		jButtonList = new ArrayList<JButton>();


		for(int i=0;i<4;i++){
			JButton button = new JButton();
			button.setName(options.get(i).getName());
			button.setIcon(options.get(i).getImage());
			jButtonList.add(button);
			optionPanel.add(button);

			button.addMouseListener(new MouseListener(){
				@Override
				public void mouseClicked(MouseEvent e) {
					endTime = System.currentTimeMillis();
					System.out.println("end "+endTime);
					Point point = e.getPoint();
					int cursorX = e.getX();
					int cursorY = e.getY();
					int check = checkAnswer(e.getComponent().getName(),question,rule);
					if(check == 0){
						errorPanel.removeAll();
						errorPanel.revalidate();
						optionPanel.revalidate();
						questionPanel.revalidate();
						errorPanel.add(new JLabel("Please try again"));
						int checkNewRule = 0;
						if(counter == nmaxgames){
							appendLogFile(resultOfThisRound); // write the output every time the rule changes
							resultOfThisRound.clear();


							checkNewRule = 1;
							int newRule = randomRule();
							if(newRule != rule){
								rule = newRule;
								nmaxgamesprev = nmaxgames;
								while(nmaxgames == nmaxgamesprev)
								{
								nmaxgames = (int)(Math.random()*4)+6; // by Z, gives integers between 6 and 10
								}

							}
							else{
								while(newRule == rule){
									newRule = randomRule();
								}
								rule = newRule;
								
								nmaxgamesprev = nmaxgames;
								while(nmaxgames == nmaxgamesprev)
								{
								nmaxgames = (int)(Math.random()*4)+6; // by Z, gives integers between 6 and 10
								}
							}
					

							counter =0;
							System.out.println(rule);
						}
						String sentence = startTime+" "+endTime+" "+nmaxgames+" " + rule+" "+checkNewRule;
						for(int i=0;i<jButtonList.size();i++){
							sentence += " "+jButtonList.get(i).getName();
						}
						sentence += " "+question+" "+check+" "+e.getComponent().getName()+" "+cursorX+" "+cursorY;

						// finalResult.add(sentence);
						resultOfThisRound.add(sentence);
					}
					else{
						counter += 1;
						optionPanel.removeAll();
						questionPanel.removeAll();
						errorPanel.removeAll();
						errorPanel.revalidate();
						optionPanel.revalidate();
						questionPanel.revalidate();
						int checkNewRule = 0;
						if(counter == nmaxgames){
							appendLogFile(resultOfThisRound); // write the output every time the rule changes
							resultOfThisRound.clear();

							checkNewRule = 1;
							int newRule = randomRule();
							if(newRule != rule){
								rule = newRule;
								nmaxgames = (int)(Math.random()*4)+6; // by Z, gives integers between 6 and 10

							}
							else{
								while(newRule == rule){
									newRule = randomRule();
								}
							}
							rule = newRule;
							nmaxgames = (int)(Math.random()*4)+6; // by Z, gives integers between 6 and 10

							counter =0;
							System.out.println(rule);
						}
						String sentence = startTime+" "+endTime+" "+nmaxgames+" "+rule+" "+checkNewRule;
						for(int i=0;i<jButtonList.size();i++){
							sentence += " "+jButtonList.get(i).getName();
						}
						sentence += " "+question+" "+check+" "+e.getComponent().getName()+" "+cursorX+" "+cursorY;

						// finalResult.add(sentence);
						resultOfThisRound.add(sentence);
						System.out.println(sentence);

						getOption();
					}

				}
				@Override
				public void mouseEntered(MouseEvent e) {}
				@Override
				public void mouseExited(MouseEvent e) {}
				@Override
				public void mousePressed(MouseEvent e) {}
				@Override
				public void mouseReleased(MouseEvent e) {}
			});
		}
	}
	public int checkAnswer(String name,String question,int rule){
		int check = 0;
		String ques = question.substring(rule-1, rule);
		String ans = name.substring(rule-1, rule);
		if(ques.equals(ans)){
			check = 1;
		}
		return check;
	}
	public List<String> random(){
		List<String> result = new ArrayList<String>();
		String shape = Integer.toString((int)(Math.random()*4)+1);
		String color = Integer.toString((int)(Math.random()*4)+1);
		String num = Integer.toString((int)(Math.random()*4)+1);
		result.add(shape);
		result.add(color);
		result.add(num);
		return result;
	}
	public String addQuestion(List<Card> selectOptions){
		String question = "";
		JLabel label = new JLabel();
		List<String> random = random();
		for(int i=0;i<selectOptions.size();i++){
			String option0 = selectOptions.get(i).getName().substring(0, 1);
			String option1 = selectOptions.get(i).getName().substring(1, 2);
			String option2 = selectOptions.get(i).getName().substring(2, 3);

			if(!((option0.equals(random.get(0))&&option1.equals(random.get(1))))&&
					!((option1.equals(random.get(1))&&option2.equals(random.get(2))))&&
					!((option0.equals(random.get(0))&&option2.equals(random.get(2))))&&
					!((option0.equals(random.get(0))&&option1.equals(random.get(1))&&option2.equals(random.get(2))))){

				if(i==selectOptions.size()-1){
					question = random.get(0)+random.get(1)+random.get(2);
				}
				else{
					continue;
				}
			}
			else{
				return addQuestion(selectOptions);
			}
		}
		for(Card card:cardList){
			if(card.getName().equals(question)){
				label.setIcon(card.getImage());
				questionPanel.add(label);
			}
		}
		return question;
	}
	public int randomRule(){
		return (int)(Math.random()*3)+1;
	}

	public void writeLogFile(List<String> finalresult){
		try {
			FileWriter writer = new FileWriter("result.txt");
			for(String result: finalresult){
				writer.write(result);
				writer.write(System.lineSeparator());
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void appendLogFile(List<String> resultOfThisRound){
		try {
			FileWriter writer = new FileWriter(outputFileName, true);
			for(String result: resultOfThisRound){
				writer.write(result);
				writer.write(System.lineSeparator());
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public void run() {
		this.setVisible(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.pack();
	}
}
