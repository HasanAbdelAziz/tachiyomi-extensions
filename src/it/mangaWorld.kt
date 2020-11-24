package eu.kanade.tachiyomi.extension.it.mangaWorld
//guys i dont know wtf im doing
import android.net.Uri;
import eu.kanade.tachiyomi.network.RequestsKt;
import eu.kanade.tachiyomi.source.model.Filter;
import eu.kanade.tachiyomi.source.model.FilterList;
import eu.kanade.tachiyomi.source.model.Page;
import eu.kanade.tachiyomi.source.model.SChapter;
import eu.kanade.tachiyomi.source.model.SManga;
import eu.kanade.tachiyomi.source.online.ParsedHttpSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.MatchGroup;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import p000eu.kanade.tachiyomi.extension.BuildConfig;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0010\u0018\u00002\u00020\u0001:\u0007?@ABCDEB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0014J\b\u0010\u0017\u001a\u00020\u0004H\u0014J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u000e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bH\u0002J\u000e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001bH\u0002J\u0010\u0010\u001f\u001a\u00020\u00042\u0006\u0010 \u001a\u00020\u0016H\u0002J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0014J\u0010\u0010%\u001a\u00020\u00042\u0006\u0010&\u001a\u00020'H\u0014J\u0010\u0010(\u001a\u00020)2\u0006\u0010\u0015\u001a\u00020\u0016H\u0014J\b\u0010*\u001a\u00020\u0004H\u0014J\u0010\u0010+\u001a\u00020\"2\u0006\u0010#\u001a\u00020,H\u0014J\b\u0010-\u001a\u00020\u0004H\u0014J\u0010\u0010.\u001a\u00020)2\u0006\u0010&\u001a\u00020'H\u0014J\u0016\u0010/\u001a\b\u0012\u0004\u0012\u00020$0\u001b2\u0006\u0010&\u001a\u00020'H\u0014J\u0010\u00100\u001a\u00020,2\u0006\u0010\u0015\u001a\u00020\u0004H\u0002J\u0010\u00101\u001a\u00020)2\u0006\u0010\u0015\u001a\u00020\u0016H\u0014J\b\u00102\u001a\u00020\u0004H\u0014J\u0010\u00103\u001a\u00020\"2\u0006\u0010#\u001a\u00020,H\u0014J\b\u00104\u001a\u00020\u0004H\u0014J\u0018\u00105\u001a\u0002062\u0006\u00107\u001a\u00020\u00142\u0006\u00108\u001a\u00020)H\u0016J\u0010\u00109\u001a\u00020)2\u0006\u0010\u0015\u001a\u00020\u0016H\u0014J\b\u0010:\u001a\u00020\u0004H\u0014J \u0010;\u001a\u00020\"2\u0006\u0010#\u001a\u00020,2\u0006\u0010<\u001a\u00020\u00042\u0006\u0010=\u001a\u00020\u0019H\u0014J\b\u0010>\u001a\u00020\u0004H\u0014R\u0014\u0010\u0003\u001a\u00020\u0004XD¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\bX\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u0004XD¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0014\u0010\r\u001a\u00020\u0004XD¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0014\u0010\u000f\u001a\u00020\u0010XD¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006F"}, d2 = {"Leu/kanade/tachiyomi/extension/it/mangaworld/Mangaworld;", "Leu/kanade/tachiyomi/source/online/ParsedHttpSource;", "()V", "baseUrl", "", "getBaseUrl", "()Ljava/lang/String;", "client", "Lokhttp3/OkHttpClient;", "getClient", "()Lokhttp3/OkHttpClient;", "lang", "getLang", "name", "getName", "supportsLatest", "", "getSupportsLatest", "()Z", "chapterFromElement", "Leu/kanade/tachiyomi/source/model/SChapter;", "element", "Lorg/jsoup/nodes/Element;", "chapterListSelector", "getFilterList", "Leu/kanade/tachiyomi/source/model/FilterList;", "getGenreList", "", "Leu/kanade/tachiyomi/extension/it/mangaworld/Mangaworld$Genre;", "getStatusList", "Leu/kanade/tachiyomi/extension/it/mangaworld/Mangaworld$Status;", "getUrl", "urlElement", "imageRequest", "Lokhttp3/Request;", "page", "Leu/kanade/tachiyomi/source/model/Page;", "imageUrlParse", "document", "Lorg/jsoup/nodes/Document;", "latestUpdatesFromElement", "Leu/kanade/tachiyomi/source/model/SManga;", "latestUpdatesNextPageSelector", "latestUpdatesRequest", "", "latestUpdatesSelector", "mangaDetailsParse", "pageListParse", "parseStatus", "popularMangaFromElement", "popularMangaNextPageSelector", "popularMangaRequest", "popularMangaSelector", "prepareNewChapter", "", "chapter", "manga", "searchMangaFromElement", "searchMangaNextPageSelector", "searchMangaRequest", "query", "filters", "searchMangaSelector", "Genre", "GenreList", "SortBy", "Status", "StatusList", "TextField", "UriPartFilter", "tachiyomi-it.mangaworld-v1.2.2_release"}, k = 1, mv = {1, 1, 16})
/* renamed from: eu.kanade.tachiyomi.extension.it.mangaworld.Mangaworld */
/* compiled from: Mangaworld.kt */
public final class Mangaworld extends ParsedHttpSource {
    private final String baseUrl = "https://www.mangaworld.cc";//this is probabli purrect url i dont know how i can test it, plz tell me how i can do it
    private final OkHttpClient client = getNetwork().getCloudflareClient();
    private final String lang = "it";
    private final String name = "Mangaworld";
    private final boolean supportsLatest = true;

    /* access modifiers changed from: protected */
    public String chapterListSelector() {
        return "li.wp-manga-chapter";
    }

    /* access modifiers changed from: protected */
    public String imageUrlParse(Document document) {
        Intrinsics.checkParameterIsNotNull(document, "document");
        return BuildConfig.FLAVOR;
    }

    /* access modifiers changed from: protected */
    public String popularMangaNextPageSelector() {
        return "div.nav-previous.float-left > a";
    }

    /* access modifiers changed from: protected */
    public String popularMangaSelector() {
        return "div.c-tabs-item__content";
    }

    public String getName() {
        return this.name;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public String getLang() {
        return this.lang;
    }

    public boolean getSupportsLatest() {
        return this.supportsLatest;
    }

    public OkHttpClient getClient() {
        return this.client;
    }

    /* access modifiers changed from: protected */
    public Request popularMangaRequest(int i) {
        return RequestsKt.GET$default(getBaseUrl() + "/page/" + i + "?s&post_type=wp-manga&m_orderby=views", getHeaders(), (CacheControl) null, 4, (Object) null);
    }

    /* access modifiers changed from: protected */
    public Request latestUpdatesRequest(int i) {
        return RequestsKt.GET$default(getBaseUrl() + "/page/" + i + "?s&post_type=wp-manga&m_orderby=latest", getHeaders(), (CacheControl) null, 4, (Object) null);
    }

    /* access modifiers changed from: protected */
    public String latestUpdatesSelector() {
        return popularMangaSelector();
    }

    /* access modifiers changed from: protected */
    public String searchMangaSelector() {
        return popularMangaSelector();
    }

    /* access modifiers changed from: protected */
    public SManga popularMangaFromElement(Element element) {
        Intrinsics.checkParameterIsNotNull(element, "element");
        return searchMangaFromElement(element);
    }

    /* access modifiers changed from: protected */
    public SManga latestUpdatesFromElement(Element element) {
        Intrinsics.checkParameterIsNotNull(element, "element");
        return searchMangaFromElement(element);
    }

    /* access modifiers changed from: protected */
    public String latestUpdatesNextPageSelector() {
        return popularMangaNextPageSelector();
    }

    /* access modifiers changed from: protected */
    public String searchMangaNextPageSelector() {
        return popularMangaNextPageSelector();
    }

    /* access modifiers changed from: protected */
    public SManga searchMangaFromElement(Element element) {
        Intrinsics.checkParameterIsNotNull(element, "element");
        SManga create = SManga.Companion.create();
        create.setThumbnail_url(element.select("div.tab-thumb > a > img").attr("src"));
        Element first = element.select("div.tab-thumb > a").first();
        String attr = first.attr("href");
        Intrinsics.checkExpressionValueIsNotNull(attr, "it.attr(\"href\")");
        setUrlWithoutDomain(create, attr);
        String attr2 = first.attr("title");
        Intrinsics.checkExpressionValueIsNotNull(attr2, "it.attr(\"title\")");
        create.setTitle(attr2);
        return create;
    }

    /* access modifiers changed from: protected */
    public Request searchMangaRequest(int i, String str, FilterList filterList) {
        Intrinsics.checkParameterIsNotNull(str, "query");
        Intrinsics.checkParameterIsNotNull(filterList, "filters");
        HttpUrl parse = HttpUrl.parse(getBaseUrl() + "/page/" + i);
        if (parse == null) {
            Intrinsics.throwNpe();
        }
        HttpUrl.Builder newBuilder = parse.newBuilder();
        newBuilder.addQueryParameter("post_type", "wp-manga");
        String replace = new Regex("\\s+").replace(str, "+");
        if (str.length() > 0) {
            newBuilder.addQueryParameter("s", replace);
        } else {
            newBuilder.addQueryParameter("s", BuildConfig.FLAVOR);
        }
        if (filterList.isEmpty()) {
            filterList = getFilterList();
        }
        for (GenreList genreList : (Iterable) filterList) {
            if (genreList instanceof GenreList) {
                List<String> arrayList = new ArrayList<>();
                for (Genre genre : (Iterable) genreList.getState()) {
                    if (((Number) genre.getState()).intValue() == 1) {
                        arrayList.add(genre.getId());
                    }
                }
                if (!arrayList.isEmpty()) {
                    for (String addQueryParameter : arrayList) {
                        newBuilder.addQueryParameter("genre[]", addQueryParameter);
                    }
                }
            } else if (genreList instanceof StatusList) {
                List<String> arrayList2 = new ArrayList<>();
                for (Status status : (Iterable) ((StatusList) genreList).getState()) {
                    if (((Number) status.getState()).intValue() == 1) {
                        arrayList2.add(status.getId());
                    }
                }
                if (!arrayList2.isEmpty()) {
                    for (String addQueryParameter2 : arrayList2) {
                        newBuilder.addQueryParameter("status[]", addQueryParameter2);
                    }
                }
            } else if (genreList instanceof SortBy) {
                newBuilder.addQueryParameter("m_orderby", ((SortBy) genreList).toUriPart());
            } else if (genreList instanceof TextField) {
                TextField textField = (TextField) genreList;
                newBuilder.addQueryParameter(textField.getKey(), (String) textField.getState());
            }
        }
        String builder = newBuilder.toString();
        Intrinsics.checkExpressionValueIsNotNull(builder, "url.toString()");
        return RequestsKt.GET$default(builder, getHeaders(), (CacheControl) null, 4, (Object) null);
    }

    /* access modifiers changed from: protected */
    public SManga mangaDetailsParse(Document document) {
        Document document2 = document;
        Intrinsics.checkParameterIsNotNull(document2, "document");
        Element first = document2.select("div.site-content").first();
        SManga create = SManga.Companion.create();
        Elements select = first.select("div.author-content");
        String str = null;
        create.setAuthor(select != null ? select.text() : null);
        Elements select2 = first.select("div.artist-content");
        create.setArtist(select2 != null ? select2.text() : null);
        List arrayList = new ArrayList();
        Iterable<Element> select3 = first.select("div.genres-content a");
        Intrinsics.checkExpressionValueIsNotNull(select3, "infoElement.select(\"div.genres-content a\")");
        for (Element text : select3) {
            String text2 = text.text();
            Intrinsics.checkExpressionValueIsNotNull(text2, "genre");
            arrayList.add(text2);
        }
        create.setGenre(CollectionsKt.joinToString$default(arrayList, ", ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null));
        String text3 = first.select("div.post-status > div:nth-child(2)  div").text();
        Intrinsics.checkExpressionValueIsNotNull(text3, "infoElement.select(\"div.…th-child(2)  div\").text()");
        create.setStatus(parseStatus(text3));
        Elements select4 = document2.select("div.summary__content > p");
        if (select4 != null) {
            str = select4.text();
        }
        create.setDescription(str);
        create.setThumbnail_url(document2.select("div.summary_image > a > img").attr("src"));
        return create;
    }

    private final int parseStatus(String str) {
        if (str != null) {
            String lowerCase = str.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
            if (StringsKt.contains$default(lowerCase, "ongoing", false, 2, (Object) null)) {
                return 1;
            }
            if (str != null) {
                String lowerCase2 = str.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(lowerCase2, "(this as java.lang.String).toLowerCase()");
                if (StringsKt.contains$default(lowerCase2, "completed", false, 2, (Object) null)) {
                    return 2;
                }
                return 0;
            }
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }

    /* access modifiers changed from: protected */
    public SChapter chapterFromElement(Element element) {
        long j;
        String text;
        Intrinsics.checkParameterIsNotNull(element, "element");
        Element first = element.select("a").first();
        SChapter create = SChapter.Companion.create();
        Intrinsics.checkExpressionValueIsNotNull(first, "urlElement");
        setUrlWithoutDomain(create, getUrl(first));
        String text2 = first.text();
        Intrinsics.checkExpressionValueIsNotNull(text2, "urlElement.text()");
        create.setName(text2);
        Element last = element.select("span.chapter-release-date i").last();
        if (last == null || (text = last.text()) == null) {
            j = 0;
        } else {
            try {
                Date parse = new SimpleDateFormat("dd MMMM yyyy", Locale.ITALY).parse(text);
                Intrinsics.checkExpressionValueIsNotNull(parse, "SimpleDateFormat(\"dd MMM…, Locale.ITALY).parse(it)");
                j = parse.getTime();
            } catch (ParseException unused) {
                Date parse2 = new SimpleDateFormat("H", Locale.ITALY).parse(text);
                Intrinsics.checkExpressionValueIsNotNull(parse2, "SimpleDateFormat(\"H\", Locale.ITALY).parse(it)");
                j = parse2.getTime();
            }
        }
        create.setDate_upload(j);
        return create;
    }

    private final String getUrl(Element element) {
        String attr = element.attr("href");
        Intrinsics.checkExpressionValueIsNotNull(attr, "url");
        if (StringsKt.endsWith$default(attr, "?style=list", false, 2, (Object) null)) {
            return attr;
        }
        return attr + "?style=list";
    }

    public void prepareNewChapter(SChapter sChapter, SManga sManga) {
        Intrinsics.checkParameterIsNotNull(sChapter, "chapter");
        Intrinsics.checkParameterIsNotNull(sManga, "manga");
        Regex regex = new Regex("Capitolo\\s([0-9]+)");
        if (regex.containsMatchIn(sChapter.getName())) {
            String str = null;
            MatchResult find$default = Regex.find$default(regex, sChapter.getName(), 0, 2, (Object) null);
            if (find$default != null) {
                MatchGroup matchGroup = find$default.getGroups().get(1);
                if (matchGroup != null) {
                    str = matchGroup.getValue();
                }
                if (str == null) {
                    Intrinsics.throwNpe();
                }
                sChapter.setChapter_number(Float.parseFloat(str));
            }
        }
    }

    /* access modifiers changed from: protected */
    public List<Page> pageListParse(Document document) {
        Intrinsics.checkParameterIsNotNull(document, "document");
        List<Page> arrayList = new ArrayList<>();
        Iterable<Element> select = document.select("div.reading-content * img");
        Intrinsics.checkExpressionValueIsNotNull(select, "document.select(\"div.reading-content * img\")");
        int i = 0;
        for (Element attr : select) {
            String attr2 = attr.attr("src");
            i++;
            if (attr2.length() != 0) {
                arrayList.add(new Page(i, BuildConfig.FLAVOR, attr2, (Uri) null, 8, (DefaultConstructorMarker) null));
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public Request imageRequest(Page page) {
        Intrinsics.checkParameterIsNotNull(page, "page");
        Headers.Builder builder = new Headers.Builder();
        builder.add("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30");
        builder.add("Referer", getBaseUrl());
        Headers build = builder.build();
        String imageUrl = page.getImageUrl();
        if (imageUrl == null) {
            Intrinsics.throwNpe();
        }
        Intrinsics.checkExpressionValueIsNotNull(build, "imgHeader");
        return RequestsKt.GET$default(imageUrl, build, (CacheControl) null, 4, (Object) null);
    }

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Leu/kanade/tachiyomi/extension/it/mangaworld/Mangaworld$TextField;", "Leu/kanade/tachiyomi/source/model/Filter$Text;", "name", "", "key", "(Ljava/lang/String;Ljava/lang/String;)V", "getKey", "()Ljava/lang/String;", "tachiyomi-it.mangaworld-v1.2.2_release"}, k = 1, mv = {1, 1, 16})
    /* renamed from: eu.kanade.tachiyomi.extension.it.mangaworld.Mangaworld$TextField */
    /* compiled from: Mangaworld.kt */
    private static final class TextField extends Filter.Text {
        private final String key;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public TextField(String str, String str2) {
            super(str, (String) null, 2, (DefaultConstructorMarker) null);
            Intrinsics.checkParameterIsNotNull(str, "name");
            Intrinsics.checkParameterIsNotNull(str2, "key");
            this.key = str2;
        }

        public final String getKey() {
            return this.key;
        }
    }

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Leu/kanade/tachiyomi/extension/it/mangaworld/Mangaworld$SortBy;", "Leu/kanade/tachiyomi/extension/it/mangaworld/Mangaworld$UriPartFilter;", "()V", "tachiyomi-it.mangaworld-v1.2.2_release"}, k = 1, mv = {1, 1, 16})
    /* renamed from: eu.kanade.tachiyomi.extension.it.mangaworld.Mangaworld$SortBy */
    /* compiled from: Mangaworld.kt */
    private static final class SortBy extends UriPartFilter {
        public SortBy() {
            super("Ordina per", new Pair[]{new Pair("Rilevanza", BuildConfig.FLAVOR), new Pair("Ultime Aggiunte", "latest"), new Pair("A-Z", "alphabet"), new Pair("Voto", "rating"), new Pair("Tendenza", "trending"), new Pair("Più Visualizzati", "views"), new Pair("Nuove Aggiunte", "new-manga")});
        }
    }

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Leu/kanade/tachiyomi/extension/it/mangaworld/Mangaworld$Genre;", "Leu/kanade/tachiyomi/source/model/Filter$TriState;", "name", "", "id", "(Ljava/lang/String;Ljava/lang/String;)V", "getId", "()Ljava/lang/String;", "tachiyomi-it.mangaworld-v1.2.2_release"}, k = 1, mv = {1, 1, 16})
    /* renamed from: eu.kanade.tachiyomi.extension.it.mangaworld.Mangaworld$Genre */
    /* compiled from: Mangaworld.kt */
    private static final class Genre extends Filter.TriState {

        /* renamed from: id */
        private final String f0id;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Genre(String str, String str2) {
            super(str, 0, 2, (DefaultConstructorMarker) null);
            Intrinsics.checkParameterIsNotNull(str, "name");
            Intrinsics.checkParameterIsNotNull(str2, "id");
            this.f0id = str2;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public /* synthetic */ Genre(String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i & 2) != 0 ? str : str2);
        }

        public final String getId() {
            return this.f0id;
        }
    }

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0004¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"Leu/kanade/tachiyomi/extension/it/mangaworld/Mangaworld$GenreList;", "Leu/kanade/tachiyomi/source/model/Filter$Group;", "Leu/kanade/tachiyomi/extension/it/mangaworld/Mangaworld$Genre;", "genres", "", "(Ljava/util/List;)V", "tachiyomi-it.mangaworld-v1.2.2_release"}, k = 1, mv = {1, 1, 16})
    /* renamed from: eu.kanade.tachiyomi.extension.it.mangaworld.Mangaworld$GenreList */
    /* compiled from: Mangaworld.kt */
    private static final class GenreList extends Filter.Group<Genre> {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public GenreList(List<Genre> list) {
            super("Generi", list);
            Intrinsics.checkParameterIsNotNull(list, "genres");
        }
    }

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Leu/kanade/tachiyomi/extension/it/mangaworld/Mangaworld$Status;", "Leu/kanade/tachiyomi/source/model/Filter$TriState;", "name", "", "id", "(Ljava/lang/String;Ljava/lang/String;)V", "getId", "()Ljava/lang/String;", "tachiyomi-it.mangaworld-v1.2.2_release"}, k = 1, mv = {1, 1, 16})
    /* renamed from: eu.kanade.tachiyomi.extension.it.mangaworld.Mangaworld$Status */
    /* compiled from: Mangaworld.kt */
    private static final class Status extends Filter.TriState {

        /* renamed from: id */
        private final String f1id;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Status(String str, String str2) {
            super(str, 0, 2, (DefaultConstructorMarker) null);
            Intrinsics.checkParameterIsNotNull(str, "name");
            Intrinsics.checkParameterIsNotNull(str2, "id");
            this.f1id = str2;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public /* synthetic */ Status(String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i & 2) != 0 ? str : str2);
        }

        public final String getId() {
            return this.f1id;
        }
    }

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0004¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"Leu/kanade/tachiyomi/extension/it/mangaworld/Mangaworld$StatusList;", "Leu/kanade/tachiyomi/source/model/Filter$Group;", "Leu/kanade/tachiyomi/extension/it/mangaworld/Mangaworld$Status;", "statuses", "", "(Ljava/util/List;)V", "tachiyomi-it.mangaworld-v1.2.2_release"}, k = 1, mv = {1, 1, 16})
    /* renamed from: eu.kanade.tachiyomi.extension.it.mangaworld.Mangaworld$StatusList */
    /* compiled from: Mangaworld.kt */
    private static final class StatusList extends Filter.Group<Status> {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public StatusList(List<Status> list) {
            super("Stato", list);
            Intrinsics.checkParameterIsNotNull(list, "statuses");
        }
    }

    public FilterList getFilterList() {
        return new FilterList(new Filter[]{(Filter) new TextField("Autore", "author"), (Filter) new TextField("Anno di rilascio", BuildConfig.BUILD_TYPE), (Filter) new SortBy(), (Filter) new StatusList(getStatusList()), (Filter) new GenreList(getGenreList())});
    }

    private final List<Status> getStatusList() {
        return CollectionsKt.listOf(new Status[]{new Status("Completato", "end"), new Status("In Corso", "on-going"), new Status("Droppato", "canceled"), new Status("In Pausa", "on-hold")});
    }

    private final List<Genre> getGenreList() {
        return CollectionsKt.listOf(new Genre[]{new Genre("Adulti", "adult"), new Genre("Anime", "anime"), new Genre("Arti Marziali", "martial-arts"), new Genre("Avventura", "adventure"), new Genre("Azione", "action"), new Genre("Cartoon", "cartoon"), new Genre("Comic", "comic"), new Genre("Commedia", "comedy"), new Genre("Cucina", "cooking"), new Genre("Demoni", "demoni"), new Genre("Detective", "detective"), new Genre("Doujinshi", "doujinshi"), new Genre("Drama", "drama-"), new Genre("Drammatico", "drama"), new Genre("Ecchi", "ecchi"), new Genre("Fantasy", "fantasy"), new Genre("Game", "game"), new Genre("Gender Bender", "gender-bender"), new Genre("Harem", "harem"), new Genre("Hentai", "hentai"), new Genre("Horror", "horror"), new Genre("Josei", "josei"), new Genre("Live action", "live-action"), new Genre("Magia", "magia"), new Genre("Manga", "manga"), new Genre("Manhua", "manhua"), new Genre("Manhwa", "manhwa"), new Genre("Mature", "mature"), new Genre("Mecha", "mecha"), new Genre("Militari", "militari"), new Genre("Mistero", "mystery"), new Genre("Musica", "musica"), new Genre("One shot", "one-shot"), new Genre("Parodia", "parodia"), new Genre("Psicologico", "psychological"), new Genre("Romantico", "romance"), new Genre("RPG", "rpg"), new Genre("Sci-fi", "sci-fi"), new Genre("Scolastico", "school-life"), new Genre("Seinen", "seinen"), new Genre("Shoujo", "shoujo"), new Genre("Shoujo Ai", "shoujo-ai"), new Genre("Shounen", "shounen"), new Genre("Shounen Ai", "shounen-ai"), new Genre("Slice of Life", "slice-of-life"), new Genre("Smut", "smut"), new Genre("Soft Yaoi", "soft-yaoi"), new Genre("Soft Yuri", "soft-yuri"), new Genre("Soprannaturale", "supernatural"), new Genre("Spazio", "spazio"), new Genre("Sport", "sports"), new Genre("Storico", "historical"), new Genre("Super Poteri", "superpower"), new Genre("Thriller", "thriller"), new Genre("Tragico", "tragedy"), new Genre("Vampiri", "vampiri"), new Genre("Webtoon", "webtoon"), new Genre("Yaoi", "yaoi"), new Genre("Yuri", "yuri")});
    }

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0012\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B'\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0018\u0010\u0004\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0006\u0010\u000b\u001a\u00020\u0002R%\u0010\u0004\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00060\u0005¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\t¨\u0006\f"}, d2 = {"Leu/kanade/tachiyomi/extension/it/mangaworld/Mangaworld$UriPartFilter;", "Leu/kanade/tachiyomi/source/model/Filter$Select;", "", "displayName", "vals", "", "Lkotlin/Pair;", "(Ljava/lang/String;[Lkotlin/Pair;)V", "getVals", "()[Lkotlin/Pair;", "[Lkotlin/Pair;", "toUriPart", "tachiyomi-it.mangaworld-v1.2.2_release"}, k = 1, mv = {1, 1, 16})
    /* renamed from: eu.kanade.tachiyomi.extension.it.mangaworld.Mangaworld$UriPartFilter */
    /* compiled from: Mangaworld.kt */
    private static class UriPartFilter extends Filter.Select<String> {
        private final Pair<String, String>[] vals;

        public final Pair<String, String>[] getVals() {
            return this.vals;
        }

        public final String toUriPart() {
            return (String) this.vals[((Number) getState()).intValue()].getSecond();
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public UriPartFilter(java.lang.String r9, kotlin.Pair<java.lang.String, java.lang.String>[] r10) {
            /*
                r8 = this;
                java.lang.String r0 = "displayName"
                kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r9, r0)
                java.lang.String r0 = "vals"
                kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r10, r0)
                java.util.ArrayList r0 = new java.util.ArrayList
                int r1 = r10.length
                r0.<init>(r1)
                java.util.Collection r0 = (java.util.Collection) r0
                int r1 = r10.length
                r2 = 0
                r3 = 0
            L_0x0015:
                if (r3 >= r1) goto L_0x0025
                r4 = r10[r3]
                java.lang.Object r4 = r4.getFirst()
                java.lang.String r4 = (java.lang.String) r4
                r0.add(r4)
                int r3 = r3 + 1
                goto L_0x0015
            L_0x0025:
                java.util.List r0 = (java.util.List) r0
                java.util.Collection r0 = (java.util.Collection) r0
                java.lang.String[] r1 = new java.lang.String[r2]
                java.lang.Object[] r4 = r0.toArray(r1)
                if (r4 == 0) goto L_0x003c
                r5 = 0
                r6 = 4
                r7 = 0
                r2 = r8
                r3 = r9
                r2.<init>(r3, r4, r5, r6, r7)
                r8.vals = r10
                return
            L_0x003c:
                kotlin.TypeCastException r9 = new kotlin.TypeCastException
                java.lang.String r10 = "null cannot be cast to non-null type kotlin.Array<T>"
                r9.<init>(r10)
                goto L_0x0045
            L_0x0044:
                throw r9
            L_0x0045:
                goto L_0x0044
            */
            throw new UnsupportedOperationException("Method not decompiled: p000eu.kanade.tachiyomi.extension.p001it.mangaworld.Mangaworld.UriPartFilter.<init>(java.lang.String, kotlin.Pair[]):void");
        }
    }
}
