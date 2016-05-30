package net.hdavid.vaadinjeeexample;

import com.vaadin.ui.*;

import java.util.Arrays;
import java.util.Collection;

public class L  {

	public static final String _NOOP = "NOOP";
	public static final String _MARGIN = "MARGIN";
	public static final String _NO_SPACING = "NO_SPACING";
	public static final String _FULL_SIZE = "SIZE_FULL";
	public static final String _FULL_HEIGHT = "FULL_HEIGHT";
	public static final String _FULL_WIDTH = "FULL_WIDHT";
    public static final String _EXPAND = "EXPAND";
    public static final String _EXPANDER = "EXPANDER";

	public static VerticalLayout  ve(Object ... componetsAndSettings) {
		if (componetsAndSettings.length == 0)
			return new VerticalLayout();
		
		VerticalLayout vl = null;
		if (componetsAndSettings[0] instanceof AbstractOrderedLayout && (!(componetsAndSettings[0] instanceof VerticalLayout)))
			throw new RuntimeException("aranging vertical items in an horizontal layout, use _NOOP if necesary as the first component");
		
        if (componetsAndSettings[0] instanceof VerticalLayout) {
        	
        	vl = (VerticalLayout) componetsAndSettings[0];
        	componetsAndSettings = Arrays.asList(componetsAndSettings).subList(1, componetsAndSettings.length).toArray();
        } else  {
        	vl = new VerticalLayout();
        }
        
            setOps(false, null, null, vl, componetsAndSettings);
            return vl;
	}

   public static HorizontalLayout ho(Object... componetsAndSettings) {
	   if (componetsAndSettings.length == 0)
			return new HorizontalLayout();
	   
       HorizontalLayout hl = null;
       if (componetsAndSettings[0] instanceof AbstractOrderedLayout && (!(componetsAndSettings[0] instanceof HorizontalLayout)))
			throw new RuntimeException("aranging horizontal items in an vertical layout, use _NOOP if necesary as the first component");
        if (componetsAndSettings[0] instanceof HorizontalLayout) {
        	hl = (HorizontalLayout) componetsAndSettings[0];
        	componetsAndSettings = Arrays.asList(componetsAndSettings).subList(1, componetsAndSettings.length).toArray();       
        } else {
        	hl = new HorizontalLayout();
        }
      	setOps(false, null, null, hl, componetsAndSettings);
        return hl;
   }

	private static void setOps(boolean defaultsAlreadyDefined, Alignment defaultAlignment,  Component lastComponent, AbstractOrderedLayout aol, Object... settings) {
		
        if (!defaultsAlreadyDefined) {
            aol.setMargin(false);
            aol.setSpacing(true);
            aol.setSizeUndefined();
            defaultAlignment = null;
            lastComponent = null;
            
        }
         
		for (Object s : settings) {

			if (s == null || s == _NOOP) {
				continue;
			} else if (s == _MARGIN) {
				aol.setMargin(true);
			} else  if (s == _NO_SPACING) {
				aol.setSpacing(false);
			} else if (s == _FULL_SIZE) {
				aol.setSizeFull();
			} else if (s == _FULL_HEIGHT) {
				if (lastComponent == null) {
					aol.setHeight("100%");
				} else {
					lastComponent.setHeight("100%");
				}
			} else if (s == _FULL_WIDTH) {
				if (lastComponent == null) {
					aol.setWidth("100%");
				} else {
                    lastComponent.setWidth("100%");
				}
            } else if (s == _EXPAND) {
                aol.setExpandRatio(lastComponent, 1.0f);
            } else if (s == _EXPANDER) {
                CssLayout expander = new CssLayout();
                expander.setSizeFull();
                expander.setStyleName("expander");
                aol.addComponent(expander);
                aol.setExpandRatio(expander, 1.0f);
            }else if (s instanceof Float) {
				aol.setExpandRatio(lastComponent, (float) s);
			} else if (s instanceof String && ((String) s).endsWith("%")) {
				if (aol instanceof VerticalLayout)
					lastComponent.setWidth(s.toString());
				else
					lastComponent.setHeight(s.toString());
			} else if (s instanceof String && ((String) s).endsWith("em")) {
				if (aol instanceof VerticalLayout)
					lastComponent.setWidth(s.toString());
				else
					lastComponent.setHeight(s.toString());
			} else if (s instanceof String) {
				if (lastComponent == null) {
					aol.addStyleName((String)s);
				} else {
					lastComponent.addStyleName((String)s);
				}
			} else  if (s instanceof Component) {
				lastComponent = (Component) s;
				aol.addComponent(lastComponent);
				if (defaultAlignment != null) {
					aol.setComponentAlignment(lastComponent, defaultAlignment);
				}
			} else  if (s instanceof Alignment) {
				if (lastComponent == null) {
					defaultAlignment = (Alignment) s;
				} else {
					aol.setComponentAlignment(lastComponent, (Alignment) s);
				}
			} else if (s instanceof Collection) {
				setOps(true, defaultAlignment, lastComponent,  aol, (Object []) ((Collection) s).toArray());
			} else {
				System.out.println("Invalid object passed for layout: "+ s.getClass().getName());
			}
		}

	}

}
