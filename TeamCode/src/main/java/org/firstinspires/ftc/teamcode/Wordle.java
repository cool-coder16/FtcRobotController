package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@TeleOp
public class Wordle extends LinearOpMode {
    List<String> words = new ArrayList<>(Arrays.asList(
            "cigar","rebut","sissy","humph","awake","blush","focal","evade","naval","serve",
            "heath","dwarf","model","karma","stink","grade","quiet","bench","abate","feign",
            "major","death","fresh","crust","stool","colon","abase","marry","react","batty",
            "pride","floss","helix","croak","staff","paper","unfed","whelp","trawl","outdo",
            "adobe","crazy","sower","repay","digit","crate","cluck","spike","mimic","pound",
            "maxim","linen","unmet","flesh","booby","forth","first","stand","belly","ivory",
            "seedy","print","yearn","drain","bribe","stout","panel","crass","flume","offal",
            "agree","error","swirl","argue","bleed","delta","flick","totem","wooer","front",
            "shrub","parry","biome","lapel","start","greet","goner","golem","lusty","loopy",
            "round","audit","lying","gamma","labor","islet","civic","forge","corny","moult",
            "basic","salad","agate","spicy","spray","essay","fjord","spend","kebab","guild",
            "aback","motor","alone","hatch","hyper","thumb","dowry","ought","belch","dutch",
            "pilot","tweed","comet","jaunt","enema","steed","abyss","growl","fling","dozen",
            "boozy","erode","world","gouge","click","briar","great","altar","pulpy","blurt",
            "coast","duchy","groin","fixer","group","rogue","badly","smart","pithy","gaudy",
            "chill","heron","vodka","finer","surer","radio","rouge","perch","retch","wrote",
            "clock","tilde","store","prove","bring","solve","cheat","grime","exult","usher",
            "epoch","triad","break","rhino","viral","conic","masse","sonic","vital","trace",
            "using","peach","champ","baton","brake","pluck","craze","gripe","weary","picky",
            "acute","ferry","aside","tapir","troll","unify","rebus","boost","truss","siege",
            "tiger","banal","slump","crank","gorge","query","drink","favor","abbey","tangy",
            "panic","solar","shire","proxy","point","robot","prick","wince","crimp","knoll",
            "sugar","whack","mount","perky","could","wrung","light","those","moist","shard",
            "pleat","aloft","skill","elder","frame","humor","pause","ulcer","ultra","robin",
            "cynic","aroma","caulk","shake","dodge","swill","tacit","other","thorn","trove",
            "bloke","vivid","spill","chant","choke","rupee","nasty","mourn","ahead","brine",
            "cloth","hoard","sweet","month","lapse","watch","today","focus","smelt","tease",
            "cater","movie","lynch","saute","allow","renew","their","slosh","purge","chest",
            "depot","epoxy","nymph","found","shall","harry","stove","lowly","snout","trope",
            "fewer","shawl","natal","comma","foray","scare","stair","black","squad","royal",
            "chunk","mince","shame","cheek","ample","flair","foyer","cargo","oxide","plant",
            "olive","inert","askew","heist","shown","zesty","hasty","trash","fella","larva",
            "forgo","story","hairy","train","homer","badge","midst","canny","fetus","butch",
            "farce","slung","tipsy","metal","yield","delve","being","scour","glass","gamer",
            "scrap","money","hinge","album","vouch","asset","tiara","crept","bayou","atoll",
            "manor","creak","showy","phase","froth","depth","gloom","flood","trait","girth",
            "piety","payer","goose","float","donor","atone","primo","apron","blown","cacao",
            "loser","input","gloat","awful","brink","smite","beady","rusty","retro","droll",
            "gawky","hutch","pinto","gaily","egret","lilac","sever","field","fluff","hydro",
            "flack","agape","voice","stead","stalk","berth","madam","night","bland","liver",
            "wedge","augur","roomy","wacky","flock","angry","bobby","trite","aphid","tryst",
            "midge","power","elope","cinch","motto","stomp","upset","bluff","cramp","quart",
            "coyly","youth","rhyme","buggy","alien","smear","unfit","patty","cling","glean",
            "label","hunky","khaki","poker","gruel","twice","twang","shrug","treat","waste",
            "merit","woven","octal","needy","clown","widow","irony","ruder","gauze","chief",
            "onset","prize","fungi","charm","gully","inter","whoop","taunt","leery","class",
            "theme","lofty","tibia","booze","alpha","thyme","eclat","doubt","parer","chute",
            "stick","trice","alike","sooth","recap","saint","liege","glory","grate","admit",
            "brisk","soggy","usurp","scald","scorn","leave","twine","sting","bough","marsh",
            "sloth","dandy","vigor","howdy","enjoy","valid","ionic","equal","floor","catch",
            "spade","stein","exist","quirk","denim","grove","spiel","mummy","fault","foggy",
            "flout","carry","sneak","libel","waltz","aptly","piney","inept","aloud","photo",
            "dream","stale","vomit","ombre","fanny","unite","snarl","baker","there","glyph",
            "pooch","hippy","spell","folly","louse","gulch","vault","godly","threw","fleet",
            "grave","inane","shock","crave","spite","valve","skimp","claim","rainy","musty",
            "pique","daddy","quasi","arise","aging","valet","opium","avert","stuck","recut",
            "mulch","genre","plume","rifle","count","incur","total","wrest","mocha","deter",
            "study","lover","safer","rivet","funny","smoke","mound","undue","sedan","pagan",
            "swine","guile","gusty","equip","tough","canoe","chaos","covet","human","udder",
            "lunch","blast","stray","manga","melee","lefty","quick","paste","given","octet",
            "risen","groan","leaky","grind","carve","loose","sadly","spilt","apple","slack",
            "honey","final","sheen","eerie","minty","slick","derby","wharf","spelt","coach",
            "erupt","singe","price","spawn","fairy","jiffy","filmy","stack","chose","sleep",
            "ardor","nanny","niece","woozy","handy","grace","ditto","stank","cream","usual",
            "diode","valor","angle","ninja","muddy","chase","reply","prone","spoil","heart",
            "shade","diner","arson","onion","sleet","dowel","couch","palsy","bowel","smile",
            "evoke","creek","lance","eagle","idiot","siren","built","embed","award","dross",
            "annul","goody","frown","patio","laden","humid","elite","lymph","edify","might",
            "reset","visit","gusto","purse","vapor","crock","write","sunny","loath","chaff",
            "slide","queer","venom","stamp","sorry","still","acorn","aping","pushy","tamer",
            "hater","mania","awoke","brawn","swift","exile","birch","lucky","freer","risky",
            "ghost","plier","lunar","winch","snare","nurse","house","borax","nicer","latch",
            "comer","batch","times","inert","cream","serum","bride","shake","eaten","index",
            "climb","plead","slice","there","force","gland","roast","silly","sugar","cigar"
    ));

    Random random = new Random();

    int newIndex = random.nextInt(words.size());

    String chosenWord = words.get(newIndex);

    public void runOpMode(){

    }
}
