package online.himakeit.skylark.model.mob;

import java.io.Serializable;
import java.util.List;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/19 18:37
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 * curPage : 1
 * list : [{"ctgIds":["0010001007","0010001020","0010001038","0010001063"],"ctgTitles":"荤菜,炖,京菜,养生","menuId":"00100010070000017758","name":"腔骨萝卜汤","recipe":{"ingredients":"[\"猪腔骨500g，最好是在超市现买的新鲜腔骨，\",\"白萝卜一整个，大概1000g\",\"超市买的炖肉料两小包,整包是五六块钱；也可以用香叶3片、桂皮5克、丁香3克、肉桂2克、大料10瓣、花椒10克制作成料包\",\"葱30克、姜20克、蒜2头\"]","method":"[{\"step\":\"1.大葱一根，切成七八厘米的葱段姜蒜若干，切成蒜瓣，姜片\"},{\"step\":\"2.腔骨在买时，就让老板给切成一节一节的小块，用冷水放在炖锅里，放入葱段，姜片，炖肉料\"},{\"step\":\"3.用小火开始烧,这样可以保证肉嫩\"},{\"step\":\"4.炖二十分钟后，将白萝卜块放入锅中一起炖，火可以开到中火，但是小心汤溢出来\"},{\"step\":\"5.再四十分钟后，放入适量盐，尝尝肉如果熟了 ，萝卜如果烂了，就可以出锅啦\"}]","sumary":"猪腔骨骨头多肉少，很适合拿来炖汤，如果在配上能够润肺的白萝卜，就更爽啦！！","title":"怎样做出浓郁的腔骨萝卜汤"}}]
 * total : 17758
 */
public class MobCookDetailEntity implements Serializable {

    private int curPage;
    private int total;
    private List<MobCookDetailListBean> list;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<MobCookDetailListBean> getList() {
        return list;
    }

    public void setList(List<MobCookDetailListBean> list) {
        this.list = list;
    }

    public static class MobCookDetailListBean implements Serializable{
        private String ctgTitles;
        private String menuId;
        private String name;
        private MobCookDetailListRecipeBean recipe;
        private List<String> ctgIds;
        private String thumbnail;

        public String getCtgTitles() {
            return ctgTitles;
        }

        public void setCtgTitles(String ctgTitles) {
            this.ctgTitles = ctgTitles;
        }

        public String getMenuId() {
            return menuId;
        }

        public void setMenuId(String menuId) {
            this.menuId = menuId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public MobCookDetailListRecipeBean getRecipe() {
            return recipe;
        }

        public void setRecipe(MobCookDetailListRecipeBean recipe) {
            this.recipe = recipe;
        }

        public List<String> getCtgIds() {
            return ctgIds;
        }

        public void setCtgIds(List<String> ctgIds) {
            this.ctgIds = ctgIds;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public static class MobCookDetailListRecipeBean implements Serializable{
            private String ingredients;
            private String method;
            private String sumary;
            private String title;
            private String img;

            public String getIngredients() {
                return ingredients;
            }

            public void setIngredients(String ingredients) {
                this.ingredients = ingredients;
            }

            public String getMethod() {
                return method;
            }

            public void setMethod(String method) {
                this.method = method;
            }

            public String getSumary() {
                return sumary;
            }

            public void setSumary(String sumary) {
                this.sumary = sumary;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}
