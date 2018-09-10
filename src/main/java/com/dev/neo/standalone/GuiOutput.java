/*
 * Copyright 2016-2017 Javier Garcia Alonso.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dev.neo.standalone;

import java.awt.EventQueue;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import com.dev.neo.JSensors;
import com.dev.neo.model.components.Component;
import com.dev.neo.model.components.Components;
import com.dev.neo.model.components.Cpu;
import com.dev.neo.model.components.Disk;
import com.dev.neo.model.components.Gpu;
import com.dev.neo.model.sensors.Fan;
import com.dev.neo.model.sensors.Load;
import com.dev.neo.model.sensors.Temperature;

/**
 * Provides an output using a Swing based GUI
 * 
 * @author Javier Garcia Alonso
 *
 */
public class GuiOutput {

	public static void showOutput(final Map<String, String> config) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JSensorsGUI gui = new GuiOutput().new JSensorsGUI(config);
				gui.setVisible(true);
			}
		});
	}

	@SuppressWarnings("serial")
	class JSensorsGUI extends JFrame {
		private Map<String, String> config;
		private JTable table = new JTable();

		public JSensorsGUI(Map<String, String> config) {
			this.config = config;
			initUI();
		}

		private void initUI() {
			setTitle("JSensors");
			setSize(600, 400);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(EXIT_ON_CLOSE);

			new GuiUpdater(this).execute();

		}

		private DefaultTableModel calculateModel() {
			DefaultTableModel model = new DefaultTableModel(new Object[] { "Sensor Name", "Value" }, 0);

			Components components = JSensors.get.config(this.config).components();

			List<Cpu> cpus = components.cpus;
			if (cpus != null) {
				for (final Cpu cpu : cpus) {
					model.addRow(new String[] { "CPU component: " + cpu.name });
					addComponent(cpu, model);
				}
			}

			List<Gpu> gpus = components.gpus;
			if (gpus != null) {
				for (final Gpu gpu : gpus) {
					model.addRow(new String[] { "GPU component: " + gpu.name });
					addComponent(gpu, model);
				}
			}

			List<Disk> disks = components.disks;
			if (disks != null) {
				for (final Disk disk : disks) {
					model.addRow(new String[] { "Disk component: " + disk.name });
					addComponent(disk, model);
				}
			}

			return model;
		}

		// Read component values in standalone mode
		private void addComponent(Component component, DefaultTableModel model) {
			if (component.sensors != null) {

				List<Temperature> temps = component.sensors.temperatures;
				for (final Temperature temp : temps) {
					model.addRow(new String[] { temp.name + ": ", temp.value + " C" });
				}

				List<Fan> fans = component.sensors.fans;
				for (final Fan fan : fans) {
					model.addRow(new String[] { fan.name + ": ", fan.value + " RPM" });
				}

				List<Load> loads = component.sensors.loads;
				for (final Load load : loads) {
					model.addRow(new String[] { load.name + ": ", load.value + "" });
				}
			}
		}

		private class GuiUpdater extends SwingWorker<Void, Void> {
			public GuiUpdater(JSensorsGUI jSensorsGUI) {
				JScrollPane scrollPane = new JScrollPane(table);
				table.setFillsViewportHeight(true);
				jSensorsGUI.add(scrollPane);

			}

			@Override
			protected Void doInBackground() throws Exception {
				while (true) {
					table.setModel(calculateModel());
					Thread.sleep(2000);
				}
			}

		}
	}

}
