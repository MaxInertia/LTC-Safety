package cmpt371group2.ltc_prototype.list_view;

import android.support.annotation.NonNull;

public class ListRow {
    private final String text;
    private final int image_resource;
    private final int layout_resource;

    public ListRow(@NonNull String text, int img_resource, int layout_resource){
        this.text = text;
        this.image_resource = img_resource;
        this.layout_resource = layout_resource;
    }

    public int getLayout() {
        return layout_resource;
    }

    public int getImage() {
        return image_resource;
    }

    public String getText() {
        return text;
    }
}
