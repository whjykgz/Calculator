package com.count;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MyCount {
	private final String[] ITEM = new String[] {"是","否"};
	private JFrame mainWindow = new JFrame("四则运算练习软件");
	private JPanel selectPanel = new JPanel(new GridLayout(4, 2,0,10));
	private JPanel mainPanel = new JPanel();
	private JPanel commandP = new JPanel();
	private JLabel JLQuestionNum = new JLabel("题目数量:");
	private JLabel JLtype=new JLabel("运算类型:");
	private JPanel JPtype = new JPanel(new FlowLayout());
	private JTextField JTFQuestionNum = new JTextField(2);
	private JLabel JBits=new JLabel("最大数值:");
	private JTextField JTbitsMax=new JTextField(3);
	private JTextField JTBits = new JTextField(8);
	private JLabel isOutTxt=new JLabel("是否输出到文件:");
	private JComboBox combobox = new JComboBox(ITEM);
    private JTextArea output=new JTextArea();
    private JButton sure=new JButton("确定");
    private JScrollPane scroll = new JScrollPane(output);
	//设置字体
	private Font JLBFont = new Font("微软雅黑",Font.BOLD,18);
	private Font JLTFont = new Font("微软雅黑",Font.BOLD,15);
	private JCheckBox checkBox01 = new JCheckBox("加减");
	private JCheckBox checkBox02 = new JCheckBox("乘除"); 
	private JCheckBox checkBox03 = new JCheckBox("小数"); 
	private JCheckBox checkBox04 = new JCheckBox("括号"); 
	public MyCount() {
		selectPanel.setSize(200,300);
		JPtype.add(checkBox01);
		JPtype.add(checkBox02);
		JPtype.add(checkBox03);
		JPtype.add(checkBox04);
		JLQuestionNum.setFont(JLBFont);
		selectPanel.add(JLQuestionNum);
		JTFQuestionNum.setFont(JLBFont);
		selectPanel.add(JTFQuestionNum);
		JLtype.setFont(JLBFont);
		selectPanel.add(JLtype);
		JPtype.setFont(JLBFont);
		selectPanel.add(JPtype);
		JBits.setFont(JLBFont);
		selectPanel.add(JBits);
		JTBits.setFont(JLBFont);
		JTbitsMax.setFont(JLBFont);
		selectPanel.add(JTbitsMax);
		isOutTxt.setFont(JLBFont);
		selectPanel.add(isOutTxt);
		selectPanel.add(combobox);
		
		
		mainPanel.setPreferredSize(new Dimension(500,400));
		mainPanel.setLayout(new BorderLayout());
		output.setFont(JLTFont);
		mainPanel.add(scroll);
		mainPanel.setVisible(true);
		
		
		commandP.setPreferredSize(new Dimension(700,40));
		commandP.add(sure);
		mainWindow.add(selectPanel,BorderLayout.CENTER); 
		mainWindow.add(mainPanel,BorderLayout.WEST); 
		mainWindow.add(commandP, BorderLayout.SOUTH); 
		mainWindow.pack(); 
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		mainWindow.setSize(800,350);//设置窗体大小 
		mainWindow.setLocationRelativeTo(null);//将窗口置于屏幕中间 
		mainWindow.setVisible(true);//设置为可见 要放在最后 放在前面则只能看见用户名和选择面板 主面板等需要拖动窗口大小才能看见
		
		
		sure.addActionListener(new ActionListener(){
			@SuppressWarnings("null")
			@Override
			public void actionPerformed(ActionEvent e) {
				int i=1;
				Boolean md=checkBox01.isSelected();
				Boolean pointNum=checkBox02.isSelected();
				Boolean brackets=checkBox03.isSelected();
				Calculator c = new Calculator();
				OutPutFile of=null;
				try {
					of=new OutPutFile();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				int length=Integer.parseInt(JTFQuestionNum.getText());
				if(combobox.getSelectedItem()=="是"){
					for(int j=0;j<length;j++){
						ArrayList<StringBuffer> array = c.create(md, pointNum, brackets,JTbitsMax.getText());
						try {
							of.write(array,j+1);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						output.append((j+1)+". ");
						for(StringBuffer sb:array) {
							output.append(sb.toString());
						}
						output.append("=\n");
					}
					try {
	                    of.close();
	                 } catch (IOException e1) {
	                    e1.printStackTrace();
	                 }
				}else{
					for(int j=0;j<length;j++){
						ArrayList<StringBuffer> array = c.create(md, pointNum, brackets,JTbitsMax.getText());
						output.append(i+". ");
						for(StringBuffer sb:array) {
							output.append(sb.toString());
						}
						output.append("\n");
						i++;
					}
				}
			}
		});
	}
	public static void main(String[] args) {
        MyCount exercise = new MyCount();
    }
}
