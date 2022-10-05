package com.idg.idgcore.coe.app.service.virtualRelationship;

import com.idg.idgcore.coe.domain.entity.virtualRelationship.VirtualRelationshipEntity;
import com.idg.idgcore.coe.domain.service.virtualRelationship.VirtualEntityRelationshipDomainService;
import com.idg.idgcore.coe.dto.virtualentity.VirtualEntityDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Iterator;
import java.util.Set;

@Slf4j
@Service
public class VirtualEntityRelationshipApplicationService implements IVirtualEntityRelationshipApplicationService {

    private static final String DEFAULT_ENTITY_KEY = "1";
    private static final String ENTITY_CODE = "entityCode";
    private static final String ENTITY_TYPE = "entityType";
    private static final String ENTITY_NAME = "entityName";
    private static final String CHILDREN = "children";
    @Autowired
    private VirtualEntityRelationshipDomainService virtualEntityRelationshipDomainService;

    private static final JSONParser jsonParser = new JSONParser();

    private JSONObject getJSONObjectFromVirtualEntityDTO(VirtualEntityDTO virtualEntityDTO) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ENTITY_CODE, virtualEntityDTO.getEntityCode());
        jsonObject.put(ENTITY_TYPE, virtualEntityDTO.getEntityType());
        jsonObject.put(ENTITY_NAME, virtualEntityDTO.getEntityName());
        jsonObject.put(CHILDREN, new JSONArray());
        return jsonObject;
    }

    @Override
    public void processVirtualEntityRelationship(VirtualEntityDTO virtualEntityDTO) throws FatalException, org.json.simple.parser.ParseException {
        VirtualRelationshipEntity virtualRelationshipEntity = virtualEntityRelationshipDomainService.getById(DEFAULT_ENTITY_KEY);
        if (null != virtualRelationshipEntity) {
            JSONObject baseJsonObject = (JSONObject) jsonParser.parse(virtualRelationshipEntity.getJsonData());
            addChild(baseJsonObject, virtualEntityDTO.getParentEntityCode(), getJSONObjectFromVirtualEntityDTO(virtualEntityDTO));
            save(baseJsonObject.toJSONString());
        } else {
            save(getJSONObjectFromVirtualEntityDTO(virtualEntityDTO).toJSONString());
        }
    }

    private void save(String jsonData) {
        VirtualRelationshipEntity relationshipEntity = new VirtualRelationshipEntity();
        relationshipEntity.setId(DEFAULT_ENTITY_KEY);
        relationshipEntity.setJsonData(jsonData);
        virtualEntityRelationshipDomainService.save(relationshipEntity);
    }

    private static void addChild(JSONObject jsonObject, String parentId, JSONObject childData) throws JSONException {
        Set<String> keySet = jsonObject.keySet();
        Iterator<String> jsonIterator = keySet.iterator();

        String keyStr = "";
        boolean isEntityFound = false;
        while (jsonIterator.hasNext()) {
            keyStr = jsonIterator.next();
            Object keyvalue = jsonObject.get(keyStr);
            if (keyvalue.equals(parentId)) {
                isEntityFound = true;
            }
            if (keySet.isEmpty() && keySet.size() == 0) {
                jsonObject.put(parentId, childData);
                return;
            }
            if (keyvalue instanceof JSONArray && isEntityFound == true) {
                ((JSONArray) keyvalue).add(childData);
            } else if (keyvalue instanceof JSONArray && isEntityFound != true) {
                addChildArray((JSONArray) keyvalue, parentId, childData);
            }
        }
        System.out.println(jsonObject);
    }

    private static void addChildArray(JSONArray jsonArray, String parentId, JSONObject childJSONObject) throws JSONException {
        for (int i = 0; i < jsonArray.size(); i++) {
            if (jsonArray.get(i) instanceof JSONObject) {
                addChild((JSONObject) jsonArray.get(i), parentId, childJSONObject);
            }
            System.out.println("");
        }
    }
}
