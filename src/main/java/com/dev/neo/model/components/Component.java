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
package com.dev.neo.model.components;

import com.dev.neo.model.sensors.Sensors;

/**
 *
 * @author javier
 */
public abstract class Component {
	public final String name;
	public final Sensors sensors;

	public Component(String name, Sensors sensors) {
		this.name = name;
		this.sensors = sensors;
	}
}
