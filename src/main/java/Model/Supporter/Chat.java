package Model.Supporter;

import GUI.MenuHandler;

import java.util.ArrayList;

public class Chat {
//    private String supporterUsername;
//    private String userUsername;
    private String firstPerson;
    private String secondPerson;
    private ArrayList<String> sender = new ArrayList<>();
    private ArrayList<String> message = new ArrayList<>();
    private static ArrayList<Chat> allChats = new ArrayList<>();
    private boolean hasUnreadMessage;

    public Chat(String firstPerson, String secondPerson) {
        this.firstPerson = firstPerson;
        this.secondPerson = secondPerson;
        this.hasUnreadMessage = true;
        allChats.add(this);
    }

    public static void processMessage(String sender, String receiver, String content, ArrayList<Chat> myChat) {
        Chat selectedChat = getChat(sender, receiver, myChat);
        selectedChat.addMessage(sender, content);
    }

    public static Chat getChat(String sender, String receiver, ArrayList<Chat> myChat) {
        for (Chat chat : myChat) {
            if ((chat.getSecondPerson().equals(sender) & chat.getFirstPerson().equals(receiver)) |
                    ((chat.getSecondPerson().equals(receiver) & (chat.getFirstPerson().equals(sender))))) {
                return chat;
            }
        }
        Chat chat = new Chat(sender, receiver);
        myChat.add(chat);
        return chat;
    }

    public void addMessage(String sender, String message) {
        synchronized (MenuHandler.getNewMessageLock()) {
            this.sender.add(sender);
            this.message.add(message);
            this.hasUnreadMessage = true;
            MenuHandler.getNewMessageLock().notifyAll();
        }
    }

    public boolean hasUnreadMessage() {
        return hasUnreadMessage;
    }

    public void setMessagesRead() {
        this.hasUnreadMessage = false;
    }

    public ArrayList<String> getSender() {
        return sender;
    }

    public ArrayList<String> getMessage() {
        return message;
    }

    public String getMessagesStringFormatted() {
        return sender.toString() + " - " + message.toString();
    }

    public String getFirstPerson() {
        return firstPerson;
    }

    public String getSecondPerson() {
        return secondPerson;
    }

    public static Chat getChatWith(String personName, ArrayList<Chat> myChats) {
        for (Chat chat : myChats) {
            if (personName.equalsIgnoreCase(chat.getFirstPerson()) | personName.equalsIgnoreCase(chat.getSecondPerson())) {
                return chat;
            }
        }
        return null;
    }

    public ArrayList<Chat> getChatsOfSupporterWithUsername(String supporterUsername) {
        ArrayList<Chat> buffer = new ArrayList<>();
        for (Chat chat : allChats) {
            if (chat.firstPerson.equals(supporterUsername)) {
                buffer.add(chat);
            }
        }
        return buffer;
    }

    public ArrayList<Chat> getChatsOfAccountWithUsername(String userUsername) {
        ArrayList<Chat> buffer = new ArrayList<>();
        for (Chat chat : allChats) {
            if (chat.secondPerson.equals(userUsername)) {
                buffer.add(chat);
            }
        }
        return buffer;
    }

}
