package org.ubot.client.ui.screens.theater;

import org.ubot.bot.Bot;
import org.ubot.client.Client;
import org.ubot.util.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BotTheaterScreen extends JPanel implements ActionListener {

	private final Client client;
	private final JPanel theaterPanel;

	public BotTheaterScreen(Client client) {
		super(new BorderLayout());
		this.client = client;
		this.theaterPanel = new JPanel(new GridLayout(0, 3));
		add(theaterPanel, BorderLayout.CENTER);
	}

	public final void displayPreviews(ArrayList<Bot> bots) {
		theaterPanel.removeAll();
		for (Bot bot : bots) {
			theaterPanel.add(generateBotPreview(bot));
		}
		theaterPanel.revalidate();
	}

	private final BotPreview generateBotPreview(Bot bot) {
		return new BotPreview(bot, this::actionPerformed);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final BotPreview source = (BotPreview) e.getSource();
		client.displayScreen(source.getBot());
	}

	private class BotPreview extends JButton {

		private final Bot bot;

		public BotPreview(Bot bot, ActionListener listener) {
			super(bot.getName());
			this.bot = bot;
			this.addActionListener(listener);
			setPreferredSize(new Dimension(267, 176));
		}

		public Bot getBot() {
			return bot;
		}

		@Override
		protected void paintComponent(final Graphics g) {
			super.paintComponent(g);
			if (bot.getGameCanvas() != null) {
				g.drawImage(bot.getGameCanvas().getGameBuffer(), 0, 0, null);
			} else {
				g.drawImage(Utilities.getImage("resources/Map.png"), 0, 0, null);
			}
		}
	}

}
