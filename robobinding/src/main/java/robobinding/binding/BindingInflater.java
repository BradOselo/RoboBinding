/**
 * Copyright 2011 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package robobinding.binding;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
public class BindingInflater
{
	private LayoutInflaterProvider layoutInflaterProvider = new LayoutInflaterProvider();
	private BindingFactoryProvider bindingFactoryProvider = new BindingFactoryProvider();
	
	public InflationResult inflateView(Context context, int resourceId)
	{
		LayoutInflater layoutInflater = layoutInflaterProvider.getLayoutInflater(context);
		BindingFactory bindingFactory = bindingFactoryProvider.getBindingFactory(layoutInflater);
		
		View rootView = layoutInflater.inflate(resourceId, null, false);
		List<WidgetAttributeBinder<? extends View>> viewAttributeBinders = bindingFactory.getViewAttributeBinders();
		
		return new InflationResult(rootView, viewAttributeBinders);
	}

	static class InflationResult
	{
		private View rootView;
		private List<WidgetAttributeBinder<? extends View>> viewAttributeBinders;
		
		public InflationResult(View rootView, List<WidgetAttributeBinder<? extends View>> viewAttributeBinders)
		{
			this.rootView = rootView;
			this.viewAttributeBinders = viewAttributeBinders;
		}

		public View getRootView()
		{
			return rootView;
		}

		public List<WidgetAttributeBinder<? extends View>> getViewAttributeBinders()
		{
			return viewAttributeBinders;
		}
		
	}
	
	void setLayoutInflaterProvider(LayoutInflaterProvider layoutInflaterProvider)
	{
		this.layoutInflaterProvider = layoutInflaterProvider;
	}
	
	void setBindingFactoryProvider(BindingFactoryProvider bindingFactoryProvider)
	{
		this.bindingFactoryProvider = bindingFactoryProvider;
	}
	
	static class LayoutInflaterProvider
	{
		LayoutInflater getLayoutInflater(Context context)
		{
			return LayoutInflater.from(context).cloneInContext(context);
		}
	}
	
	static class BindingFactoryProvider
	{
		BindingFactory getBindingFactory(LayoutInflater layoutInflater)
		{
			BindingFactory bindingFactory = new BindingFactory(layoutInflater);
			layoutInflater.setFactory(bindingFactory);
			return bindingFactory;
		}
	}
}
