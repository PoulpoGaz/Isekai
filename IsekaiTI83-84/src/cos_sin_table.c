#include "cos_sin_table.h"

const float cosinus[256] = {
    1.0,
    0.9996964519419704,
    0.9987859920507286,
    0.9972691732790189,
    0.9951469158335163,
    0.9924205091361342,
    0.9890916079452357,
    0.9851622332233244,
    0.9806347681859191,
    0.9755119657394484,
    0.9697969339080479,
    0.9634931422645214,
    0.9566044178153836,
    0.949134942677499,
    0.9410892515391358,
    0.9324722289069751,
    0.9232890971501027,
    0.913545448756933,
    0.9032471906016856,
    0.8924005747154039,
    0.8810121860349011,
    0.8690889384050715,
    0.8566380703815228,
    0.8436671408360789,
    0.8301840243678182,
    0.8161969065224374,
    0.8017142788228386,
    0.7867449336139606,
    0.7712979587249815,
    0.7553827319521342,
    0.7390089153654845,
    0.7221864494431263,
    0.7049255138325032,
    0.6872366531626926,
    0.6691305738890123,
    0.6506182681392562,
    0.6317109746596755,
    0.6124201719919808,
    0.5927575715047663,
    0.5727351102835846,
    0.5523649438839904,
    0.5316594389519516,
    0.5106311657161082,
    0.48929289035643764,
    0.46765756725395763,
    0.44573833112617456,
    0.42354848905304826,
    0.40110151239831826,
    0.37841102863109266,
    0.3554908130526667,
    0.33235478043359284,
    0.30901697656607985,
    0.2854915697368483,
    0.2617928421256213,
    0.23793518113447046,
    0.2139330706532822,
    0.18980108226664652,
    0.16555386640750638,
    0.1412061434629398,
    0.11677269483747092,
    0.09226835397933958,
    0.06770799737517456,
    0.043106535518538415,
    0.018478903857826873,
    -0.006160039895962541,
    -0.030795150303573847,
    -0.05541156509948214,
    -0.07999433975742247,
    -0.10452855017392217,
    -0.12899930172866408,
    -0.15339173832694986,
    -0.17769105141877625,
    -0.20188248898904562,
    -0.22595136451345485,
    -0.2498830658746251,
    -0.27366306423305964,
    -0.2972769228475437,
    -0.3207103058396313,
    -0.34394898689690034,
    -0.36697885790968904,
    -0.3897859375360723,
    -0.41235637968987854,
    -0.4346764819465923,
    -0.4567326938620414,
    -0.4785116251988169,
    -0.5000000540554322,
    -0.5211849348932862,
    -0.5420534064565575,
    -0.5625927995802201,
    -0.5827906448814439,
    -0.6026346803297056,
    -0.6221128586910184,
    -0.6412133548417597,
    -0.6599245729476552,
    -0.6782351535035636,
    -0.6961339802297865,
    -0.7136101868207168,
    -0.7306531635417294,
    -0.7472525636703079,
    -0.7633983097774985,
    -0.7790805998458766,
    -0.7942899132203112,
    -0.8090170163879177,
    -0.8232529685836855,
    -0.8369891272183806,
    -0.8502171531254265,
    -0.862929015623579,
    -0.8751169973923205,
    -0.8867736991570134,
    -0.8978920441809712,
    -0.9084652825617155,
    -0.9184869953288157,
    -0.9279510983408193,
    -0.9368518459789104,
    -0.9451838346350511,
    -0.9529420059924901,
    -0.9601216500966473,
    -0.9667184082145075,
    -0.9727282754807898,
    -0.9781476033292852,
    -0.9829731017078865,
    -0.987201841075966,
    -0.9908312541828868,
    -0.9938591376265714,
    -0.996283653191178,
    -0.9981033289630734,
    -0.9993170602244263,
    -0.9999241101238773,
    -0.9999241078169603,
    -0.9993170533051112,
    -0.9981033174355606,
    -0.9962836370624659,
    -0.9938591169064517,
    -0.9908312288839385,
    -0.9872018112135481,
    -0.9829730673001285,
    -0.9781475643970756,
    -0.9727282320477644,
    -0.9667183603070342,
    -0.9601215977438106,
    -0.9529419492260731,
    -0.9451837734895165,
    -0.9368517804913796,
    -0.9279510285510495,
    -0.9184869212791757,
    -0.9084652042971607,
    -0.8978919617490158,
    -0.8867736126077014,
    -0.8751169067781955,
    -0.8629289209996527,
    -0.8502170545491445,
    -0.8369890247495883,
    -0.823252862284591,
    -0.8090169063230551,
    -0.7942897994565004,
    -0.779080482452183,
    -0.7633981888251917,
    -0.7472524392328174,
    -0.7306530356946005,
    -0.7136100556415651,
    -0.6961338457982502,
    -0.6782350159012557,
    -0.6599244322581135,
    -0.6412132111503959,
    -0.6221127120850677,
    -0.602634530898171,
    -0.5827904927150455,
    -0.5625926447713369,
    -0.542053249099174,
    -0.5211847750829328,
    -0.49999989188912974,
    -0.47851146077501516,
    -0.45673252728056213,
    -0.43467631330856565,
    -0.41235620909768395,
    -0.3897857650932765,
    -0.3669786837209804,
    -0.3439488110680291,
    -0.32071012847734154,
    -0.2972767440595122,
    -0.27366288412782724,
    -0.24988288456153365,
    -0.2259511821025779,
    -0.20188230559112497,
    -0.17769086714515117,
    -0.15339155328949294,
    -0.12899911603971004,
    -0.10452836394620216,
    -0.0799941531039953,
    -0.05541137813366344,
    -0.030794963138870705,
    -0.006159852646001009,
    0.018478997468596613,
    0.0431066290582674,
    0.06770809078707431,
    0.09226844720670098,
    0.11677278782369513,
    0.14120623615157615,
    0.16555395874228307,
    0.1898011741915082,
    0.2139331621124218,
    0.23793527207236267,
    0.2617929324870589,
    0.28549165946697247,
    0.3090170656104166,
    0.33235486873808284,
    0.35549090056370136,
    0.3784111152955436,
    0.40110159816357277,
    0.4235485738670378,
    0.4457384149374097,
    0.4676576500115561,
    0.48929297201015826,
    0.5106312462163787,
    0.5316595182499012,
    0.5523650219314779,
    0.5727351870332269,
    0.5927576469099696,
    0.6124202460069662,
    0.6317110472395095,
    0.6506183392398752,
    0.669130643467252,
    0.6872367211763117,
    0.7049255802402119,
    0.722186481823866,
    0.7390089469034529,
    0.755382762628184,
    0.77129798852049,
    0.7867449625108391,
    0.8017143068035434,
    0.816196933569982,
    0.8301840504657818,
    0.8436671659686178,
    0.8566380945333788,
    0.8690889615615823,
    0.8810122081820084,
    0.8924005958396626,
    0.9032472106902707,
    0.9135454677976493,
    0.9232891151313902,
    0.9324722373624462,
    0.941089259454301,
    0.9491349500475533,
    0.9566044246358528,
    0.9634931485312644,
    0.9697969396172604,
    0.9755119708876644,
    0.9806347727700131,
    0.9851622352319188,
    0.9890916096691582,
    0.9924205105743382,
    0.9951469169851287,
    0.9972691737111796,
    0.9987859923389817,
    0.9996964520140555,
    1.0
};

const float sinuss[256] = {
    0.0,
    0.024637450652120793,
    0.04925994400364108,
    0.07385252891663326,
    0.09840028408008,
    0.12288829499173658,
    0.14730170091451136,
    0.1716256805680086,
    0.19584547843886693,
    0.21994636777895923,
    0.24391372856514135,
    0.26773301030922314,
    0.2913897524212045,
    0.31486959298824124,
    0.3381582774937045,
    0.3612416674710405,
    0.38410577069831736,
    0.40673666303333644,
    0.42912062717860844,
    0.4512440739196658,
    0.4730935722011078,
    0.49465585728053996,
    0.5159178387815458,
    0.5368666086408018,
    0.5574894489445102,
    0.577773839649394,
    0.5977074661835636,
    0.6172782269226453,
    0.6364742405366275,
    0.6552838532029689,
    0.6736956456815868,
    0.6916984402474324,
    0.7092813404763876,
    0.7264336050526167,
    0.7431448547133735,
    0.759404944126304,
    0.7752040018566227,
    0.7905324363600222,
    0.80538094180566,
    0.8197405037256913,
    0.8336024044879167,
    0.8469582285882203,
    0.85979986775959,
    0.8721195258946122,
    0.8839097237784581,
    0.8951633036294846,
    0.9058734334446947,
    0.9160336111474195,
    0.9256376685347018,
    0.934679775021988,
    0.9431544411828523,
    0.9510565220816052,
    0.9583812203967638,
    0.9651240893334854,
    0.9712810353231998,
    0.9768483205087972,
    0.9818225650138673,
    0.9862007489946079,
    0.9899802144731599,
    0.9931586669512555,
    0.9957341768031984,
    0.997705180447332,
    0.9990704812952833,
    0.999829250478407,
    0.9999810267742484,
    0.9995257168866544,
    0.998463598962539,
    0.9967953178094157,
    0.9945218862340526,
    0.9916446844276013,
    0.988165459127689,
    0.9840863225579806,
    0.9794097511458562,
    0.9741385840189823,
    0.968276021281689,
    0.9618256220721988,
    0.954791302401889,
    0.9471773327778965,
    0.9389883356105101,
    0.9302292824069237,
    0.9209054907530551,
    0.9110226210852598,
    0.9005866732539038,
    0.8896039828808787,
    0.8780812175132702,
    0.866025372575518,
    0.8534437671225217,
    0.8403440393962713,
    0.8267341421887028,
    0.8126223380135884,
    0.7980171940903991,
    0.7829275771431793,
    0.7673626480175951,
    0.7513318561194214,
    0.7348449336778459,
    0.7179118898370714,
    0.7005430045798057,
    0.6827488224863246,
    0.6645401463329003,
    0.6459280305334786,
    0.6269237744285896,
    0.6075389154255639,
    0.5877852219942177,
    0.5676746865222628,
    0.547219518034777,
    0.5264321347821532,
    0.5053251567010304,
    0.48391139775278014,
    0.46220385814420306,
    0.4402157164351551,
    0.41796032153789714,
    0.39545118461302414,
    0.3727019708668939,
    0.34972649125553523,
    0.3265386941000721,
    0.3031526566187553,
    0.27958257638073786,
    0.25584276268678935,
    0.23194762788217674,
    0.20791167860698767,
    0.18374950698920764,
    0.15947578178589786,
    0.13510523947785205,
    0.11065267532313813,
    0.0861329343749568,
    0.06156090246926946,
    0.03695149718766717,
    0.012319658800966991,
    -0.012319846040269913,
    -0.036951684313297435,
    -0.06156108936762384,
    -0.08613312093257007,
    -0.11065286142675192,
    -0.13510542501448358,
    -0.15947596664290864,
    -0.18374969105437167,
    -0.20791186176855975,
    -0.23194781002896023,
    -0.2558429437082041,
    -0.27958275616688605,
    -0.30315283506048946,
    -0.326538871089061,
    -0.34972666668432917,
    -0.3727021446289911,
    -0.39545135660293446,
    -0.41796049165120613,
    -0.4402158845685876,
    -0.462204024195686,
    -0.4839115616215044,
    -0.5053253182875118,
    -0.5264322939882934,
    -0.5472196747639224,
    -0.5676748406792643,
    -0.5877853734854868,
    -0.6075390641591308,
    -0.6269239203141588,
    -0.6459281734824832,
    -0.6645402862585568,
    -0.682748959303685,
    -0.7005431382058084,
    -0.7179120201905926,
    -0.7348450606797486,
    -0.7513319796926033,
    -0.7673627680870357,
    -0.7829276936359844,
    -0.7980173069358467,
    -0.8126224471431702,
    -0.8267342475361671,
    -0.8403441408976616,
    -0.8534438647162171,
    -0.8660254662022697,
    -0.878081307116238,
    -0.8896040684056647,
    -0.9005867546485867,
    -0.9110226983004249,
    -0.9209055637418253,
    -0.9302293511249882,
    -0.9389884000161499,
    -0.9471773928320117,
    -0.9547913580680206,
    -0.9618256733165524,
    -0.968276068073154,
    -0.9741386263291523,
    -0.9794097889490444,
    -0.9840863558312372,
    -0.9881654878508137,
    -0.9916447085831566,
    -0.9945219058073735,
    -0.9967953327886194,
    -0.9984636093385317,
    -0.9995257226531369,
    -0.99998102792772,
    -0.9998292487482828,
    -0.9990704772593538,
    -0.9977051741080475,
    -0.9957341681644073,
    -0.9931586560182025,
    -0.9899802012524823,
    -0.986200733494332,
    -0.9818225472434032,
    -0.9768483004789332,
    -0.9712810130460962,
    -0.9651240648226663,
    -0.9583811936671098,
    -0.9510564931493436,
    -0.9431544100655479,
    -0.9346797417385319,
    -0.9256376331053005,
    -0.9160335735935816,
    -0.9058733937892195,
    -0.8951632618964462,
    -0.883909679993193,
    -0.8721194800837019,
    -0.8597998199508465,
    -0.8469581788106677,
    -0.8336023527717745,
    -0.8197404501023569,
    -0.8053808863076872,
    -0.7905323790211043,
    -0.7752039427115694,
    -0.7594048832110224,
    -0.7431447920648446,
    -0.7264335407088747,
    -0.7092812744764947,
    -0.6916984064394445,
    -0.6736956110860821,
    -0.6552838178409509,
    -0.6364742044295637,
    -0.6172781900924561,
    -0.5977074286526092,
    -0.5777738014404584,
    -0.557489410080791,
    -0.5368665691458919,
    -0.5159177986794234,
    -0.49465581659555,
    -0.47309353095795076,
    -0.4512440321433795,
    -0.4291205848945558,
    -0.4067366202671872,
    -0.3841057274760355,
    -0.3612416456449528,
    -0.3381582554659214,
    -0.31486957077213473,
    -0.2913897300302619,
    -0.2677329877570388,
    -0.24391370586540573,
    -0.21994634494545412,
    -0.19584545548545357,
    -0.1716256690383159,
    -0.14730168933883145,
    -0.12288828337709785,
    -0.09840027243353279,
    -0.07385252308094144,
    -0.049259938159072925,
    -0.024637447727173422,
    0
};