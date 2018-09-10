package com.dev.neo.standalone;

import java.util.List;
import java.util.Map;

import com.dev.neo.JSensors;
import com.dev.neo.model.components.Components;
import com.dev.neo.model.components.Cpu;
import com.dev.neo.model.sensors.Temperature;

public class ConsoleOutput {
	
	static Double avg_cputemp;
	
	public static Double showOutput(Map<String, String> config) {
		System.out.println("Scanning sensors data...");
		Components components = JSensors.get.config(config).components();

		List<Cpu> cpus = components.cpus;
		
		if (cpus != null) {
			for (final Cpu cpu : cpus) {
				System.out.println("Found CPU component: " + cpu.name);
				for(final Cpu cputemp: cpus){
					List<Temperature> temps= cputemp.sensors.temperatures;
					Double cputemperature=0.0;
					for(Temperature temp: temps){
						
						Double holder_obj=temp.value;
						cputemperature=cputemperature+holder_obj;
					}
					avg_cputemp=cputemperature/temps.size()-1;
				}
			}
		}
		return avg_cputemp;
	}
}
