package pl.teksusik.kick4j.categories;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Verifies the CSV query-parameter building of our own V2 request DTO.
 */
public class GetCategoriesV2RequestTest {

    @Test
    public void joins_arrays_as_csv_and_maps_param_names() {
        Map<String, Object> params = GetCategoriesV2Request.builder()
                .cursor("abcd")
                .limit(50)
                .names(List.of("Rust", "Just Chatting"))
                .tags(List.of("FPS", "Shooter"))
                .ids(List.of(1, 2, 3))
                .build()
                .toQueryParams();

        assertEquals("abcd", params.get("cursor"));
        assertEquals(50, params.get("limit"));
        assertEquals("Rust,Just Chatting", params.get("name"));
        assertEquals("FPS,Shooter", params.get("tag"));
        assertEquals("1,2,3", params.get("id"));
    }

    @Test
    public void omits_null_and_empty_filters() {
        Map<String, Object> params = GetCategoriesV2Request.builder()
                .limit(10)
                .names(List.of())
                .build()
                .toQueryParams();

        assertEquals(10, params.get("limit"));
        assertFalse(params.containsKey("name"));
        assertFalse(params.containsKey("tag"));
        assertFalse(params.containsKey("id"));
        assertFalse(params.containsKey("cursor"));
    }
}
