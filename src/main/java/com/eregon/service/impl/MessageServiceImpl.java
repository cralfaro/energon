package com.eregon.service.impl;

import com.eregon.exception.MessageException;
import com.eregon.exception.UserException;
import com.eregon.model.Message;
import com.eregon.model.MessageResponse;
import com.eregon.model.User;
import com.eregon.service.MessageService;
import com.eregon.service.UserService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by ruben on 26/06/16.
 */
@Service
public class MessageServiceImpl implements MessageService {

    private Map<Character, Integer> characterIndex = new HashMap<>();
    private Map<Character, Integer> characterSlicedIndex = new HashMap<>();

    private final int SLICE_INDEX = 20;
    private final char[] ORIGINAL_ALPHABET = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private final char[] SLICED_ALPHABET = {'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G'};
    private final char[] ALPHABET_DOUBLE = ArrayUtils.addAll(ORIGINAL_ALPHABET, ORIGINAL_ALPHABET);
    private final char[] SLICED_ALPHABET_DOUBLE = ArrayUtils.addAll(SLICED_ALPHABET, SLICED_ALPHABET);

    List<Message> messageStorage = new ArrayList<>();

    @Autowired
    UserService userService;


    @Override
    public boolean postMessage(Message message) throws MessageException, UserException {
        if(!validUsers(message)){
            throw new MessageException("Sender and receiver must to be registered members");
        }
        if(!validMessageText(message)){
            throw new MessageException("The message must to be in UPPERCASE and not spaces");
        }
        message.setMessage(encodeMessage(message.getMessage()));
        messageStorage.add(message);
        return true;
    }

    private String encodeMessage(String message) {
        String decodedWord = "";
        Map<Character, Integer> characterIntegerMap = getAlphabetMap();
        for(int i = 0; i < message.length(); i++){
            int encodedIndexChar = characterIntegerMap.get(message.charAt(i)).intValue() + SLICE_INDEX;
            decodedWord += ALPHABET_DOUBLE[encodedIndexChar];
        }
        return decodedWord;
    }

    private boolean validMessageText(Message message) {
        return StringUtils.isAllUpperCase(message.getMessage());
    }

    private boolean validUsers(Message message) throws UserException{
        if(!userService.isExistingUser(message.getSender()) || !userService.isExistingUser(message.getReceiver())){
            return false;
        }
        message.setSenderName(userService.getUser(message.getSender()).getName());
        message.setReceiverName(userService.getUser(message.getReceiver()).getName());
        return true;
    }

    @Override
    public List<MessageResponse> retrieveMessages(User user) {
        List<MessageResponse> results = new ArrayList<>();
        List<Message> messagesByUser = messageStorage.stream().filter(m -> (m.getReceiver().equals(user.getUuid()) || m.getSender().equals(user.getUuid())))
                                                              .collect(Collectors.toList());
        MessageResponse response;
        for(Message message : messagesByUser){
            response = new MessageResponse();
            response.setSender(message.getSenderName());
            response.setReceiver(message.getReceiverName());
            response.setMessage(decodeMessage(message.getMessage()));
            results.add(response);
        }
        return results;
    }

    @Override
    public void removeAll() {
        messageStorage = new ArrayList<>();
    }

    @Override
    public void removeAllMessages(String uuid) {
        List<Message> messagesByUser = messageStorage.stream().filter(m -> (m.getReceiver().equals(uuid) || m.getSender().equals(uuid)))
                .collect(Collectors.toList());

        messageStorage.removeAll(messagesByUser);
    }

    private String decodeMessage(String message) {
        String decodedWord = "";
        Map<Character, Integer> characterIntegerMap = getSlicedAlphabetMap();
        for(int i = 0; i < message.length(); i++){
            int encodedIndexChar = SLICED_ALPHABET.length + characterIntegerMap.get(message.charAt(i)).intValue() - SLICE_INDEX;
            decodedWord += SLICED_ALPHABET_DOUBLE[encodedIndexChar];
        }
        return decodedWord;
    }

    public Map<Character,Integer> getSlicedAlphabetMap() {
        if(characterSlicedIndex.isEmpty()){
            buildCharacterSlicedIndexMap();
        }
        return characterSlicedIndex;
    }

    public Map<Character,Integer> getAlphabetMap() {
        if(characterIndex.isEmpty()){
            buildCharacterIndexMap();
        }
        return characterIndex;
    }

    private void buildCharacterIndexMap() {
        for(int i = 0; i < ORIGINAL_ALPHABET.length; i++) {
            characterIndex.put(Character.valueOf(ORIGINAL_ALPHABET[i]), new Integer(i));
        }
    }

    private void buildCharacterSlicedIndexMap() {
        for(int i = 0; i < SLICED_ALPHABET.length; i++) {
            characterSlicedIndex.put(Character.valueOf(SLICED_ALPHABET[i]), new Integer(i));
        }
    }
}
