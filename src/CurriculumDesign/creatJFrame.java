package CurriculumDesign;

import java.io.*;
import java.util.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class creatJFrame extends JFrame{

	Object[][] rowData1, rowData2;
	final Object[] columnNames1 = { "作者", "论文名字", "发表的期刊", "卷号", "期号", "页码", "年份" };
	final Object[] columnNames2 = { "作者", "论文名字", "发表的会议", "页码", "年份" };
	JTable table2, table3;
	JPanel pan1, pan2;
	JFrame jf1, jf2;

	private void creatJTable() {

		rowData1 = new Object[128][7];
		rowData2 = new Object[32][5];

		try {
			FileInputStream rf = new FileInputStream("papers.bib");
			BufferedReader br = new BufferedReader(new InputStreamReader(rf));

			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			Map<String, String> map = new HashMap<String, String>();
			String str = null;
			int temp = 0, temp2 = 0, row = 0;// 存放共有几条数据

			// 读取前面三行
			for (int i = 0; i < 3; i++) {
				br.readLine();
			}

			// 还未读取到文件末尾
			while ((str = br.readLine()) != null) {

				// 还未读取到每个article的末尾‘}’
				if (str.length() > 1) {

					if ((str.substring(0, 8)).equals("@article")) {

						while ((str = br.readLine()).length() > 1) {

							try {
								// 长度小于5的行直接舍弃
								if (str.length() > 5) {
									if (str.substring(0, 6).equals("author")) {
										map.put("author", str.substring(str.indexOf('{') + 1, str.indexOf("},")));
									}
									if (str.substring(0, 7).equals("journal")) {
										map.put("journal", str.substring(str.indexOf('{') + 1, str.indexOf("},")));
									}
									if (str.substring(0, 6).equals("number")) {
										map.put("number", str.substring(str.indexOf('{') + 1, str.indexOf("},")));
									}
									if (str.substring(0, 5).equals("pages")) {
										map.put("pages", str.substring(str.indexOf('{') + 1, str.indexOf("},")));
									}
									if (str.substring(0, 5).equals("title")) {
										map.put("title", str.substring(str.indexOf('{') + 2, str.indexOf("}}")));
									}
									if (str.substring(0, 6).equals("volume")) {
										map.put("volume", str.substring(str.indexOf('{') + 1, str.indexOf("},")));
									}
									if (str.substring(0, 4).equals("year")) {
										map.put("year", str.substring(str.indexOf('{') + 1, str.indexOf('}')));
									}
								}
							} catch (StringIndexOutOfBoundsException e) {
								e.printStackTrace();
							}
						}

						if (map != null && map.size() > 0) {
							// 将map中空的值补全，格式化，使输出整洁
							if (map.get("author") == null) {
								map.put("author", "");
							}
							if (map.get("journal") == null) {
								map.put("journal", "");
							}
							if (map.get("number") == null) {
								map.put("number", "");
							}
							if (map.get("pages") == null) {
								map.put("pages", "");
							}
							if (map.get("title") == null) {
								map.put("title", "");
							}
							if (map.get("volume") == null) {
								map.put("volume", "");
							}
							if (map.get("year") == null) {
								map.put("year", "");
							}
						}

						if (map != null && map.size() > 0) {
							list.add(map);
							row++;
						}
						// 将map中的值存放到rowData中
						rowData1[temp][0] = map.get("author");
						rowData1[temp][1] = map.get("title");
						rowData1[temp][2] = map.get("journal");
						rowData1[temp][3] = map.get("volume");
						rowData1[temp][4] = map.get("number");
						rowData1[temp][5] = map.get("pages");
						rowData1[temp][6] = map.get("year");
						temp++;

						map = new HashMap<String, String>();
					}
					// "@inproceedings"长度为14，不加if语句会越界
					if (str.length() > 13) {
						if ((str.substring(0, 14)).equals("@inproceedings")) {
							while ((str = br.readLine()).length() != 1 && str.length() != 0) {

								try {
									if (str.substring(0, 6).equals("author")) {
										map.put("author", str.substring(str.indexOf('{') + 1, str.indexOf("},")));
									}
									if (str.substring(0, 9).equals("booktitle")) {
										map.put("booktitle", str.substring(str.indexOf('{') + 1, str.indexOf("},")));
									}
									if (str.substring(0, 5).equals("pages")) {
										map.put("pages", str.substring(str.indexOf('{') + 1, str.indexOf("},")));
									}
									if (str.substring(0, 5).equals("title")) {
										map.put("title", str.substring(str.indexOf('{') + 2, str.indexOf("},")));
									}
									if (str.substring(0, 4).equals("year")) {
										map.put("year", str.substring(str.indexOf('{') + 1, str.indexOf('}')));
									}
								} catch (StringIndexOutOfBoundsException e) {
									e.printStackTrace();
								}
							}
							// 将map中空的值补全，格式化，方便输出整洁
							if (map.get("author") == null) {
								map.put("author", "");
							}
							if (map.get("booktitle") == null) {
								map.put("booktitle", "");
							}
							if (map.get("pages") == null) {
								map.put("pages", "");
							}
							if (map.get("title") == null) {
								map.put("title", "");
							}
							if (map.get("year") == null) {
								map.put("year", "");
							}

							if (map != null && map.size() > 0) {
								list.add(map);
								row++;
							}
							// 将map中的值存放到rowData中
							rowData2[temp2][0] = map.get("author");
							rowData2[temp2][1] = map.get("title");
							rowData2[temp2][2] = map.get("booktitle");
							rowData2[temp2][3] = map.get("pages");
							rowData2[temp2][4] = map.get("year");
							temp2++;

							map = new HashMap<String, String>();
						}
					}
				}
			}
			br.close();
			rf.close();

			// 将数据输出到console
			Iterator<Map<String, String>> it = list.iterator();
			while (it.hasNext()) {
				System.out.println(it.next());
			}
			System.out.println("共有" + row + "条数据");

		} catch (IOException e) {
			e.printStackTrace();
		}

		// 创建表格,并设置表格不能被编辑
		table2 = new JTable(rowData1, columnNames1) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table3 = new JTable(rowData2, columnNames2) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// 设置表格属性
		table2.setRowHeight(50);// 设置每行的高度
		table2.getColumnModel().getColumn(3).setPreferredWidth(20);
		table2.getColumnModel().getColumn(4).setPreferredWidth(20);
		table2.getColumnModel().getColumn(6).setPreferredWidth(20);
		table2.setRowSelectionAllowed(true);// 设置可否被选择，默认为false
		table2.setSelectionBackground(new Color(214, 200, 75));// 设置所选择行的背景颜色
		table2.setSelectionForeground(Color.black);// 设置所选择行的前景色
		table2.setGridColor(Color.GRAY);// 设置网格线的颜色
		table2.setShowHorizontalLines(true);// 是否显示水平的网格线
		table2.setShowVerticalLines(true);// 是否显示垂直的网格线
		table2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		table2.setPreferredScrollableViewportSize(new Dimension(800, 450));

		table3.setRowHeight(50);// 设置每行的高度
		table3.getColumnModel().getColumn(3).setPreferredWidth(20);
		table3.getColumnModel().getColumn(4).setPreferredWidth(20);
		table3.setRowSelectionAllowed(true);// 设置可否被选择，默认为false
		table3.setSelectionBackground(new Color(214, 200, 75));// 设置所选择行的背景颜色
		table3.setSelectionForeground(Color.BLACK);// 设置所选择行的前景色
		table3.setGridColor(Color.GRAY);// 设置网格线的颜色
		table3.setShowHorizontalLines(true);// 是否显示水平的网格线
		table3.setShowVerticalLines(true);// 是否显示垂直的网格线
		table3.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		table3.setPreferredScrollableViewportSize(new Dimension(800, 450));

		// 设置表格间隔色
		DefaultTableCellRenderer ter = new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				// table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
				if (row % 2 == 0)
					setBackground(new Color(101, 147, 74));
				else if (row % 2 == 1)
					setBackground(new Color(160, 191, 124));
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		};
		for (int i = 0; i < 7; i++) {
			table2.getColumn(columnNames1[i]).setCellRenderer(ter);
		}
		for (int i = 0; i < 5; i++) {
			table3.getColumn(columnNames2[i]).setCellRenderer(ter);
		}

		// 创建窗口中将要用到的面板
		JScrollPane pane2 = new JScrollPane(table2);
		JScrollPane pane3 = new JScrollPane(table3);
		pan1 = new JPanel(new GridLayout(0, 1));
		pan2 = new JPanel(new GridLayout(0, 1));
		pan1.add(pane2);
		pan2.add(pane3);

		// 创建窗口并设置属性
		jf2 = new JFrame("@inproceedings");
		jf2.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		jf2.setContentPane(pan2);
		jf2.pack();
		jf2.setVisible(true);
		jf2.setLocation(300, 60);

		jf1 = new JFrame("@article");
		jf1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf1.setContentPane(pan1);
		jf1.pack();
		jf1.setVisible(true);
		jf1.setLocation(400, 80);
	}

	private void creatJMune() {

		// 设置菜单栏
		JMenuItem Order1 = new JMenuItem("排序并恢复默认(O)"), select1 = new JMenuItem("筛选(S)");
		Order1.setMnemonic(KeyEvent.VK_O);
		select1.setMnemonic(KeyEvent.VK_S);

		JMenu mFile1 = new JMenu("菜单(M)");
		mFile1.setMnemonic(KeyEvent.VK_M);
		mFile1.add(Order1);
		mFile1.add(select1);

		JMenuItem font1 = new JMenuItem("字体(F)"), font3 = new JMenuItem("字体风格(L)"), size1 = new JMenuItem("大小(Z)"),
				default1 = new JMenuItem("恢复默认(D)");
		font1.setMnemonic(KeyEvent.VK_F);
		font3.setMnemonic(KeyEvent.VK_L);
		size1.setMnemonic(KeyEvent.VK_Z);
		default1.setMnemonic(KeyEvent.VK_D);

		JMenu text1 = new JMenu("文字(T)");
		text1.setMnemonic(KeyEvent.VK_T);
		text1.add(size1);
		text1.add(font1);
		text1.add(font3);
		text1.add(default1);

		JMenuItem Order2 = new JMenuItem("排序并恢复默认(O)"), select2 = new JMenuItem("筛选(S)");
		Order2.setMnemonic(KeyEvent.VK_O);
		select2.setMnemonic(KeyEvent.VK_S);

		JMenu mFile2 = new JMenu("菜单(M)");
		mFile2.setMnemonic(KeyEvent.VK_M);
		mFile2.add(Order2);
		mFile2.add(select2);

		JMenuItem font2 = new JMenuItem("字体(F)"), font4 = new JMenuItem("字体风格(L)"), size2 = new JMenuItem("大小(Z)"),
				default2 = new JMenuItem("恢复默认(D)");
		font2.setMnemonic(KeyEvent.VK_F);
		font4.setMnemonic(KeyEvent.VK_L);
		size2.setMnemonic(KeyEvent.VK_Z);
		default2.setMnemonic(KeyEvent.VK_D);

		JMenu text2 = new JMenu("文字(T)");
		text2.setMnemonic(KeyEvent.VK_T);
		text2.add(size2);
		text2.add(font2);
		text2.add(font4);
		text2.add(default2);

		// 将菜单添加到菜单栏上
		JMenuBar mb1 = new JMenuBar();
		mb1.add(mFile1);
		mb1.add(text1);

		JMenuBar mb2 = new JMenuBar();
		mb2.add(mFile2);
		mb2.add(text2);

		jf1.setJMenuBar(mb1);
		jf2.setJMenuBar(mb2);

		// 排序按钮添加事件
		Order1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frm1 = new JFrame("排序");
				JButton jb1 = new JButton("确定");
				JButton jb2 = new JButton("取消");
				Container c = frm1.getContentPane();
				c.setLayout(null);

				JLabel jl = new JLabel("点击表头可进行升序降序");
				jl.setBounds(32, 20, 150, 50);
				c.add(jl);
				c.add(jb1);
				c.add(jb2);
				jb1.setBounds(25, 80, 60, 30);
				jb2.setBounds(115, 80, 60, 30);
				c.setPreferredSize(new Dimension(200, 120));

				frm1.setContentPane(c);
				frm1.pack();
				frm1.setVisible(true);
				frm1.setLocation(680, 300);
				frm1.setResizable(false);

				// 确认按钮添加事件
				jb1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						final TableRowSorter ts = new TableRowSorter(table2.getModel());
						table2.setRowSorter(ts);

						frm1.dispose();// 关闭小窗口
					}

				});

				// 取消按钮添加事件
				jb2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frm1.dispose();
					}
				});

			}
		});

		// 筛选按钮添加事件
		select1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFrame frm2 = new JFrame("筛选");
				JButton jb3 = new JButton("筛选");
				JButton jb4 = new JButton("取消");
				Container c2 = frm2.getContentPane();
				c2.setLayout(null);

				JLabel jl2 = new JLabel("请输入要筛选的数据：");
				JTextField jtf = new JTextField();

				c2.add(jl2);
				c2.add(jtf);
				c2.add(jb3);
				c2.add(jb4);
				jl2.setBounds(40, 10, 150, 50);
				jtf.setBounds(60, 60, 80, 30);
				jb3.setBounds(25, 110, 60, 30);
				jb4.setBounds(115, 110, 60, 30);
				c2.setPreferredSize(new Dimension(200, 160));

				frm2.setContentPane(c2);
				frm2.pack();
				frm2.setVisible(true);
				frm2.setLocation(680, 300);
				frm2.setResizable(false);

				// 筛选按钮添加事件
				jb3.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						final TableRowSorter ts2 = new TableRowSorter(table2.getModel());
						table2.setRowSorter(ts2);
						String text = jtf.getText();

						if (text.length() == 0) {
							ts2.setRowFilter(null);
						} else {
							ts2.setRowFilter(RowFilter.regexFilter(text));
						}

						frm2.dispose();
					}

				});

				// 取消按钮添加事件
				jb4.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frm2.dispose();
					}
				});

			}
		});

		// 文字的恢复默认按钮添加事件
		default1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				table2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
				table2.setRowHeight(50);

			}
		});

		// 字体风格按钮添加事件
		font3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frm3 = new JFrame("字体风格");
				JButton jb5 = new JButton("确认");
				JButton jb6 = new JButton("取消");
				Container c2 = frm3.getContentPane();
				c2.setLayout(null);

				JLabel jl2 = new JLabel("请选择字体风格：");
				JComboBox cbxFont = new JComboBox();

				// 添加字体名称
				cbxFont.addItem("加粗");
				cbxFont.addItem("斜体");
				cbxFont.addItem("平体");

				c2.add(jl2);
				c2.add(cbxFont);
				c2.add(jb5);
				c2.add(jb6);
				jl2.setBounds(55, 10, 150, 50);
				cbxFont.setBounds(60, 60, 80, 30);
				jb5.setBounds(25, 110, 60, 30);
				jb6.setBounds(115, 110, 60, 30);
				c2.setPreferredSize(new Dimension(200, 160));

				frm3.setContentPane(c2);
				frm3.pack();
				frm3.setVisible(true);
				frm3.setLocation(680, 300);
				frm3.setResizable(false);

				// 确认按钮添加事件
				jb5.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						int index = cbxFont.getSelectedIndex();
						Object str1 = cbxFont.getItemAt(index);

						Object o1 = table2.getFont().getName();// 获取当前字体名字
						Object o2 = table2.getFont().getSize();// 获取当前字体大小

						if (str1.equals("斜体")) {
							table2.setFont(new Font((String) o1, Font.ITALIC, (int) o2));
						}
						if (str1.equals("加粗")) {
							table2.setFont(new Font((String) o1, Font.BOLD, (int) o2));
						}
						if (str1.equals("平体")) {
							table2.setFont(new Font((String) o1, Font.PLAIN, (int) o2));
						}

						frm3.dispose();
					}

				});

				// 取消按钮添加事件
				jb6.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frm3.dispose();
					}
				});

			}
		});

		// 字体按钮添加事件
		font1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frm4 = new JFrame("字体");
				JButton jb7 = new JButton("确认");
				JButton jb8 = new JButton("取消");
				Container c3 = frm4.getContentPane();
				c3.setLayout(null);

				JLabel jl2 = new JLabel("请选择字体：");
				JComboBox cbxFont1 = new JComboBox();

				// 添加字体名称
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				String[] fontList = ge.getAvailableFontFamilyNames();

				// 添加字体名称
				for (int i = 0; i < fontList.length; i++) {
					cbxFont1.addItem(fontList[i]);
				}

				c3.add(jl2);
				c3.add(cbxFont1);
				c3.add(jb7);
				c3.add(jb8);
				jl2.setBounds(65, 10, 150, 50);
				cbxFont1.setBounds(60, 60, 80, 30);
				jb7.setBounds(25, 110, 60, 30);
				jb8.setBounds(115, 110, 60, 30);
				c3.setPreferredSize(new Dimension(200, 160));

				frm4.setContentPane(c3);
				frm4.pack();
				frm4.setVisible(true);
				frm4.setLocation(680, 300);
				frm4.setResizable(false);

				// 确认按钮添加事件
				jb7.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						int index = cbxFont1.getSelectedIndex();
						Object str1 = cbxFont1.getItemAt(index);// 获取当前字体
						Object o4 = table2.getFont().getSize();// 获取当前字体大小
						table2.setFont(new Font(str1.toString(), Font.PLAIN, (int) o4));

						frm4.dispose();
					}

				});

				// 取消按钮添加事件
				jb8.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frm4.dispose();
					}
				});

			}
		});

		// 大小按钮添加事件
		size1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frm4 = new JFrame("字体风格");
				JButton jb7 = new JButton("确认");
				JButton jb8 = new JButton("取消");
				Container c3 = frm4.getContentPane();
				c3.setLayout(null);

				JLabel jl3 = new JLabel("请选择字体大小：");
				JComboBox jcb = new JComboBox();

				// 添加名称
				jcb.addItem("15");
				jcb.addItem("20");
				jcb.addItem("25");
				jcb.addItem("30");
				jcb.addItem("35");

				c3.add(jl3);
				c3.add(jcb);
				c3.add(jb7);
				c3.add(jb8);
				jl3.setBounds(55, 10, 150, 50);
				jcb.setBounds(60, 60, 80, 30);
				jb7.setBounds(25, 110, 60, 30);
				jb8.setBounds(115, 110, 60, 30);
				c3.setPreferredSize(new Dimension(200, 160));

				frm4.setContentPane(c3);
				frm4.pack();
				frm4.setVisible(true);
				frm4.setLocation(680, 300);
				frm4.setResizable(false);

				// 确认按钮添加事件
				jb7.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						int index = jcb.getSelectedIndex();
						String str1 = jcb.getItemAt(index).toString();
						int textSize = Integer.parseInt(str1);

						Object o3 = table2.getFont().getName();// 获取当前字体名字

						table2.setFont(new Font((String) o3, Font.PLAIN, textSize));
						table2.setRowHeight(textSize + 30);// 设置每行的高度

						frm4.dispose();
					}

				});

				// 取消按钮添加事件
				jb8.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frm4.dispose();
					}
				});

			}
		});
	}

	public void centerWindow() {
		// 获得显示屏桌面窗口的大小
		Toolkit tk = getToolkit();
		Dimension dm = tk.getScreenSize();
		// 让窗口居中显示
		setLocation((int) (dm.getWidth() - getWidth()) / 2, (int) (dm.getHeight() - getHeight()) / 2);
	}
	
	public void setLookStyle() {
		// 设置外观风格
		try {
			String lfClassName = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
			UIManager.setLookAndFeel(lfClassName);
		} catch (Exception e) {
		}
	}

	creatJFrame() {
		creatJTable();
		creatJMune();
		setLookStyle();
		centerWindow();
	}

	public static void main(String[] args) {
		new creatJFrame();
	}

}
