package com.campuskart.demo.controllers;

import com.campuskart.demo.dto.ChatRequest;
import com.campuskart.demo.models.SellItem;
import com.campuskart.demo.repositories.SellRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AiChatController {
    @Autowired
    SellRepo sellRepo;
    @PostMapping("/chat")
    public Map<String, Object> chat(@RequestBody ChatRequest req) throws Exception {

        List<SellItem> items = sellRepo.findAll();
        String msg = req.getMessage().toLowerCase();
        List<SellItem> matchedItems = items.stream()
                .filter(i -> msg.contains(i.getItemname().toLowerCase()))
                .toList();

        StringBuilder itemContext = new StringBuilder("Current items on CampusKart:\n");

        for(SellItem item : items) {
            itemContext.append("- ")
                    .append(item.getItemname())
                    .append(": ₹")
                    .append(item.getPrice())
                    .append("\n");
        }
        String prompt = """
You are CampusKart AI Assistant.
RESPONSE FORMATTING RULES (VERY STRICT):
- Every list item MUST be on a new line
- NEVER write multiple points in one sentence
- NEVER write: 1. item 2. item 3. item
- ALWAYS format like:

1. First point
2. Second point
3. Third point

- Use newline after every number

Your behavior:

1. If the question is about CampusKart marketplace:
   - Show ONLY the product list provided below
   - Give step-by-step instructions
   - Be short and clear
   - Help user buy/sell/search/order items

2. If the question is about available products:
   - Show items from the provided list only

3. If the question is general knowledge (engineering, studies, tools, concepts, definitions, etc):
   - Answer normally like a teacher
   - Give clear explanation or list
   - Do NOT mention CampusKart
   - Do NOT tell user to search online

4. Never invent products or prices.

Available CampusKart products:
%s

User question:
%s
""".formatted(itemContext.toString() , req.getMessage());

        URL url = new URL("http://localhost:11434/api/generate");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> payload = new HashMap<>();
        payload.put("model", "mistral");
        payload.put("prompt", prompt);
        payload.put("stream", false);

        String jsonBody = mapper.writeValueAsString(payload);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonBody.getBytes());
        }

        BufferedReader br =
                new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String response = br.readLine();

        JsonNode node = mapper.readTree(response);

        // ✅ small change
        Map<String, Object> res = new HashMap<>();
        res.put("answer", node.get("response").asText());
        res.put("items", matchedItems);

        return res;

    }

}

