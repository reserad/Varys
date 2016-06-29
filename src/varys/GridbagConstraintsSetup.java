package varys;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GridbagConstraintsSetup 
{
	public static GridBagConstraints getConstraints(int gridx, int gridy, int weightx, int weighty, int gridheight, int gridwidth, int fill, Insets insets) 
	{
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = gridx;
		constraints.gridy = gridy;
		constraints.weightx = weightx;
		constraints.weighty = weighty;
		constraints.gridheight = gridheight;
		constraints.gridwidth = gridwidth;
		constraints.fill = fill;
		constraints.insets = insets;
		return constraints;
	}
}
