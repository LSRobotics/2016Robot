package org.usfirst.frc.team5181.robot;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import edu.wpi.first.wpilibj.DriverStation;
import javax.imageio.ImageIO;

public class Bear {

	public void wonderfulbear() throws IOException {

		int width = 100;
		int height = 30;

		BufferedImage image = ImageIO.read(new File("/var/bear/bear.jpg"));

		for (int y = 0; y < height; y++) {
			StringBuilder sb = new StringBuilder();
			for (int x = 0; x < width; x++) {

				sb.append(image.getRGB(x, y) == -16777216 ? " " : "$");

			}

			if (sb.toString().trim().isEmpty()) {
				continue;
			}

			DriverStation.reportError(sb.toString(), false);
		}

	}

}
