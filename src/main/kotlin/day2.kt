//fun isSafeReport(levels: List<Int>): Boolean {
    // Check if the levels are strictly increasing or decreasing
    //val isIncreasing = levels.zipWithNext().all { (a, b) -> b > a && (b - a) in 1..3 }
    //val isDecreasing = levels.zipWithNext().all { (a, b) -> b < a && (a - b) in 1..3 }

    //return isIncreasing || isDecreasing
//}

fun countSafeReports(input: String): Int {
    // Parse the input into a list of reports
    val reports = input.lines().map { line ->
        line.split("\\s+".toRegex()).map { it.toInt() }
    }

    // Count the number of safe reports
    return reports.count { isSafeReport(it) }
}

fun main() {
    // Input data in the specified format
    val input = """
        9 12 14 16 17 18 15
        86 88 91 94 95 95
        15 18 20 21 23 25 28 32
        70 72 74 77 78 83
        57 60 62 64 63 64 65
        44 45 44 47 46
        33 35 32 33 36 36
        83 86 88 89 87 88 90 94
        56 59 62 65 68 65 68 75
        36 39 40 40 43
        87 90 93 95 95 97 94
        90 91 92 92 95 95
        11 14 14 15 16 19 23
        35 37 39 39 44
        59 61 63 67 68 69
        86 87 91 92 91
        33 36 38 39 43 44 47 47
        82 84 86 89 93 94 98
        4 5 7 10 13 17 24
        51 54 60 63 64 67 68
        38 40 43 45 51 54 56 53
        8 9 10 15 16 17 17
        24 26 33 34 38
        61 62 64 66 68 74 80
        44 43 46 48 49 50 51 53
        5 2 5 8 6
        12 11 14 16 17 18 18
        3 1 3 4 7 9 13
        60 58 59 62 63 70
        45 44 41 42 43 44 46
        12 11 13 12 15 13
        56 53 50 52 53 53
        70 69 72 69 70 71 72 76
        53 51 54 57 55 58 64
        26 24 26 28 28 29 31 33
        33 32 35 35 38 40 43 42
        82 81 81 82 85 86 86
        49 47 47 50 51 55
        39 36 36 39 40 41 48
        65 62 66 67 70 72 75
        20 18 22 24 26 23
        53 51 53 57 60 63 65 65
        22 20 23 26 29 33 37
        47 46 48 51 55 56 63
        55 53 60 63 65
        36 35 36 39 40 47 44
        3 2 3 4 9 9
        55 54 61 64 65 69
        17 16 19 24 26 28 29 34
        87 87 90 92 94
        66 66 68 70 72 69
        69 69 71 72 75 77 77
        74 74 77 78 82
        84 84 85 86 88 95
        55 55 57 58 59 60 59 62
        94 94 95 93 95 94
        38 38 41 38 40 40
        69 69 70 73 76 79 76 80
        4 4 6 3 8
        74 74 74 77 78
        34 34 34 37 36
        55 55 55 57 58 60 62 62
        37 37 39 40 40 41 44 48
        50 50 53 55 56 56 57 64
        11 11 12 16 17
        3 3 6 9 10 14 15 13
        71 71 74 76 80 82 85 85
        2 2 3 6 8 12 14 18
        86 86 87 91 93 94 99
        61 61 62 69 70 71
        61 61 64 71 74 76 79 78
        82 82 84 90 93 94 94
        72 72 73 75 82 83 87
        8 8 11 17 23
        7 11 14 15 17 18
        19 23 25 27 28 30 31 30
        63 67 70 72 74 76 78 78
        51 55 57 60 63 67
        42 46 48 50 53 55 62
        16 20 21 24 27 29 26 27
        37 41 40 42 45 48 46
        90 94 97 99 96 96
        43 47 48 50 52 51 55
        76 80 83 86 83 86 87 93
        81 85 85 88 90
        21 25 25 27 30 29
        11 15 15 17 17
        80 84 84 85 86 88 91 95
        3 7 7 8 14
        24 28 32 33 36 39
        57 61 65 66 68 70 72 70
        9 13 14 18 21 24 24
        28 32 34 35 37 41 42 46
        56 60 64 65 72
        5 9 14 16 17 19 21
        40 44 47 48 49 54 53
        50 54 57 64 64
        7 11 17 18 22
        67 71 73 78 80 82 85 92
        28 34 36 38 40 42 43 45
        69 76 79 82 79
        53 60 63 66 67 70 70
        51 57 60 61 62 66
        78 84 87 88 91 97
        87 92 90 93 94
        31 37 36 39 40 41 38
        2 8 9 12 11 11
        78 85 87 86 88 90 93 97
        27 32 30 33 35 38 40 46
        48 55 58 61 63 63 66
        78 85 88 88 89 88
        52 59 62 64 65 65 65
        52 58 59 62 65 65 69
        33 40 43 43 46 51
        6 12 14 18 20
        51 56 60 63 61
        18 23 24 28 29 31 31
        5 12 13 17 18 19 23
        64 70 73 77 82
        61 66 71 72 74 75 76
        11 16 23 26 29 30 28
        70 77 78 84 84
        57 62 65 72 76
        20 25 28 29 35 38 40 46
        84 81 78 75 73 76
        13 10 8 7 4 4
        12 11 10 9 7 3
        99 97 94 93 86
        95 94 92 94 93 91 90 87
        13 11 9 7 8 5 7
        32 30 28 29 29
        94 92 91 90 91 90 86
        65 62 59 61 59 54
        36 34 34 31 30 28
        53 50 49 46 43 42 42 43
        91 88 85 83 83 80 80
        75 72 70 70 68 65 61
        9 7 7 6 1
        87 84 82 78 75
        46 45 41 39 36 33 31 34
        91 88 86 83 80 77 73 73
        66 64 62 59 58 56 52 48
        33 31 29 28 24 18
        78 75 73 72 65 64
        20 17 15 8 11
        55 53 50 44 44
        46 44 43 37 33
        98 96 90 89 88 86 79
        69 71 69 66 63
        31 33 30 27 30
        91 92 90 87 86 83 81 81
        82 84 82 81 77
        53 55 53 51 46
        38 41 44 41 40
        95 97 96 94 97 94 93 96
        32 33 30 33 33
        46 48 47 49 45
        83 85 84 81 79 82 75
        77 78 77 77 76
        11 12 12 11 8 5 8
        81 82 82 81 78 76 76
        92 95 92 91 89 89 86 82
        21 22 19 18 18 15 14 9
        37 38 34 31 29
        28 31 28 24 22 20 17 20
        42 44 42 41 40 36 33 33
        67 68 64 61 57
        73 74 70 69 67 60
        79 80 78 76 75 69 66 64
        80 83 76 75 73 70 72
        96 97 92 89 89
        44 47 45 39 38 35 31
        68 70 67 65 58 57 52
        51 51 50 47 46 45 42 39
        53 53 50 47 45 43 46
        66 66 65 63 61 58 58
        49 49 46 44 40
        17 17 16 14 13 6
        66 66 65 62 64 61
        57 57 55 56 55 58
        95 95 94 96 96
        65 65 67 65 64 62 58
        35 35 32 33 32 25
        57 57 57 54 52 51 50 49
        47 47 47 45 42 45
        42 42 42 39 39
        54 54 53 51 51 47
        19 19 19 16 11
        56 56 54 50 48 45
        80 80 77 76 75 74 70 73
        90 90 88 85 84 82 78 78
        29 29 28 24 20
        88 88 85 84 83 82 78 72
        18 18 12 9 8 6 4
        18 18 12 10 12
        98 98 95 90 89 86 84 84
        41 41 39 34 30
        98 98 97 95 93 87 82
        96 92 90 87 84
        65 61 58 57 54 53 54
        38 34 32 30 28 25 22 22
        93 89 86 85 81
        58 54 52 51 50 45
        86 82 80 79 76 75 76 74
        54 50 47 49 52
        57 53 52 49 50 48 48
        64 60 57 58 55 51
        56 52 53 50 43
        90 86 85 84 84 82
        66 62 61 59 58 58 59
        86 82 81 81 78 75 75
        63 59 56 56 55 54 52 48
        86 82 79 79 78 75 70
        49 45 44 40 37 36
        95 91 87 84 82 79 76 77
        65 61 59 58 56 52 51 51
        56 52 50 46 44 41 37
        19 15 14 10 9 3
        95 91 85 84 81
        16 12 5 3 5
        95 91 84 83 82 79 76 76
        65 61 60 55 51
        72 68 67 66 59 52
        64 59 57 55 54 52 50 48
        98 91 89 88 86 85 82 83
        32 26 23 20 17 14 14
        88 81 78 76 73 69
        66 61 58 56 53 52 50 45
        63 58 59 57 55 53
        35 29 26 24 26 29
        45 40 41 39 38 37 35 35
        63 58 57 60 57 53
        37 31 30 28 27 24 25 19
        61 56 53 53 51 49
        29 22 21 20 20 18 15 17
        75 68 67 64 64 63 63
        92 85 83 82 82 81 77
        35 29 26 24 24 21 16
        40 35 34 30 27 26 25
        40 33 32 28 27 30
        25 19 17 13 13
        32 26 25 21 17
        87 80 77 76 72 71 65
        67 60 57 52 49
        22 17 11 8 10
        96 90 87 81 81
        40 33 31 30 28 26 20 16
        65 58 55 50 48 45 44 37
        75 75 77 80 85 87 88 85
        93 94 96 93 93
        9 16 13 14 14
        79 79 78 79 79
        20 19 22 23 27 28 30
        26 22 20 13 12 8
        71 64 63 57 56 53 50 50
        60 62 63 63 67
        43 38 37 35 34 37 34
        64 60 59 61 58 57 54 48
        19 14 13 11 11
        89 87 91 94 94
        3 7 10 11 14 19 21 20
        91 90 93 91 91
        26 26 24 20 17 16
        57 53 51 49 47 46 47
        35 32 29 31 28 27 20
        15 15 16 18 21 21
        18 22 23 23 26 26
        29 31 33 34 38 40 41 38
        25 19 16 12 5
        57 54 56 56 61
        7 14 17 21 23
        52 57 62 63 64 67 70 74
        79 75 73 74 77
        55 52 54 55 56 53
        56 55 52 51 51 48 48
        58 60 58 61 59
        84 86 83 77 75
        32 32 33 33 37
        54 50 47 40 40
        43 40 44 47 51
        58 58 60 61 64 65 65 72
        85 88 86 85 82 81 80 73
        42 47 49 50 52 56 55
        12 12 11 11 9 4
        22 26 30 32 35 32
        80 73 72 74 73 71 67
        22 20 17 17 13
        77 72 69 68 61
        75 74 77 79 79 79
        28 32 34 38 41 43 45
        30 30 27 25 24 22 22 18
        34 37 35 33 31 31
        73 77 80 84 90
        55 50 52 50 47 45 39
        89 85 83 79 78 81
        30 34 37 39 41 42 44 47
        26 26 25 25 23 23
        45 38 35 33 30 28 26 25
        73 77 79 79 83
        86 82 82 81 74
        29 32 30 25 18
        30 30 25 23 19
        19 12 13 11 11
        92 88 85 82 79 80 78 74
        65 64 65 68 71 74 77 77
        13 13 13 10 9 12
        74 75 77 80 82 88 86
        34 35 34 32 31 31 28 29
        17 11 9 9 7
        34 38 37 39 41
        50 49 46 46 45 42 39 42
        13 19 20 23 24 27 27 25
        33 40 45 46 46
        21 27 29 30 31 33 33
        81 74 71 65 63 61 60
        74 78 81 83 90 94
        21 21 14 11 4
        70 70 69 65 63 56
        46 50 51 51 54 57 58 57
        52 58 60 61 62 64 64 65
        42 42 36 33 32 29 28 31
        74 75 74 72 75 73 71 67
        62 62 64 67 74 75 78 85
        79 75 72 71 70 68
        53 58 59 56 57 58 61
        85 82 85 86 86 83
        4 6 8 6 6
        27 28 35 38 41 42 43 43
        94 92 90 91 90 87 86 82
        34 34 35 35 37 39 36
        18 19 20 19 16 14
        62 67 70 73 74 78 78
        64 70 75 78 81
        58 58 61 60 59 52
        85 79 75 72 68
        68 68 71 68 65 62 59 55
        75 76 74 68 66 65 65
        43 47 51 54 54
        56 59 57 53 50 48
        33 37 38 38 43
        84 79 72 70 64
        67 65 68 70 69 68
        23 22 23 24 29
        18 24 25 26 26 33
        11 16 20 22 23 26 27 31
        68 68 67 66 64
        44 38 38 37 37
        62 58 58 55 53 50 52
        11 14 15 15 18 21 21
        36 39 41 40 38 41
        42 46 49 53 54 55 57 61
        49 47 44 45 48 51 53 57
        62 68 70 71 73
        32 32 33 39 41 43 44 44
        85 82 86 89 87
        20 24 25 32 35 42
        73 73 75 77 80 84 87 93
        64 64 66 68 70 69 73
        29 27 25 22 21 19 15 9
        25 26 27 29 33 36 39
        14 19 20 22 25 27 26
        55 57 54 54 51 50
        41 34 31 29 25
        44 44 41 39 38 37 40 42
        15 13 15 16 17 20 21 24
        3 5 6 7 12 15
        13 13 15 18 21 26 30
        61 58 61 67 71
        11 8 11 14 20 22 22
        53 53 55 57 60 63
        61 59 54 53 50 48 44
        50 43 39 36 37
        53 52 51 50 46 44 42
        54 50 48 45 43 39
        65 65 62 61 59 57 53
        96 96 95 93 91 90 84
        42 44 48 51 57
        82 80 80 83 87
        78 78 82 85 88
        18 22 25 26 24 26 28 28
        17 17 20 24 25 26 27 31
        81 80 79 77 73
        35 33 29 26 25 23 24
        81 77 74 68 70
        51 53 50 49 51 46
        19 23 24 26 30
        36 36 35 34 32 32
        6 9 9 12 10
        24 22 16 14 11 9 9
        23 20 18 15 13 10 7 8
        70 71 78 79 83
        66 71 74 77 80 87 84
        31 35 38 36 39 40 43 42
        65 63 64 65 70 72
        25 25 23 19 17 14 11 13
        25 31 32 33 36 35 39
        87 83 82 80 80
        45 49 51 53 56 59 59
        84 82 79 77 76 76
        39 45 46 47 50 55 60
        16 16 14 17 19 20 23
        37 35 38 40 42 40 41 41
        82 82 85 87 89 90 94 94
        12 14 13 10 9
        63 67 69 71 73 74 71
        15 19 22 24 22 24 28
        82 82 83 89 90 91 94 95
        42 41 39 36 33 32 26 28
        10 13 14 17 17 22
        85 85 86 84 85 85
        62 57 57 56 51
        91 85 84 84 82 84
        64 65 66 69 72 74 79
        64 57 56 50 48 44
        29 29 29 27 24 22
        99 95 94 92 89 87 83 82
        79 83 85 87 94 96 97
        95 91 88 84 83 80 79 72
        86 86 84 85 84
        83 86 85 81 76
        30 29 28 26 22 19 15
        67 63 62 64 64
        6 6 10 13 14 11
        54 53 50 52 58
        37 36 36 35 33 30 27 21
        86 87 85 82 81 79 76 78
        46 50 53 56 59 65
        76 78 81 85 88 92
        62 58 57 55 54 54 50
        57 57 59 61 62 61
        83 79 78 75 74 68 66 59
        56 52 48 47 44 44
        54 50 48 46 43 43 42
        15 14 15 16 17 19 22 26
        72 73 68 67 64 63 62 58
        12 19 20 22 26 28 29 36
        26 33 30 32 35 41
        27 29 31 32 33 37
        64 64 62 61 57 54 51 51
        54 61 61 62 66
        59 63 66 71 73 76 79 79
        95 88 86 84 83 82 78 75
        61 62 61 61 61
        61 58 57 51 50 48
        39 35 38 36 34
        44 44 47 50 52 54 56 60
        42 40 38 35 32 26
        30 27 24 21 22 20 23
        19 19 22 24 27 28 34
        49 50 47 46 42 42
        82 77 79 78 76 77
        2 1 3 3 4 5 6
        77 77 76 74 69 67 66 64
        49 50 53 54 56 58 58
        83 86 84 82 78 77 74 75
        60 54 50 49 47 46 44 44
        49 48 47 47 44 42 39 36
        77 73 73 70 67 67
        31 27 22 19 18
        64 60 58 56 54 49
        62 66 68 68 70 73 75
        89 94 95 97 99 99 99
        51 53 49 47 46 43 39
        8 10 11 12 17 18 25
        87 88 90 93 91 92 95 99
        70 70 71 68 70 73 80
        57 55 61 64 62
        58 59 61 64 62 64
        26 28 30 31 33 31
        67 71 74 75 74 81
        33 34 33 31 30 28 24
        42 35 33 31 29 27 25 27
        24 22 21 17 17
        70 63 62 62 60 56
        33 35 35 38 39 40
        2 2 4 4 5 5
        43 43 41 40 36 33 29
        85 85 86 86 89 92 95
        26 32 29 32 34 36 33
        26 27 30 28 34
        51 54 54 51 50 48 44
        71 71 65 62 59 59
        58 53 48 45 44 46
        97 93 91 87 84 80
        11 10 13 14 15 16 23 28
        43 48 51 52 57
        43 42 44 48 50 56
        58 63 64 65 66 69 70 74
        80 79 76 73 68 61
        4 4 7 4 3
        82 81 80 78 77
        81 84 87 89 90 91
        67 64 62 61 58 56 53 50
        84 82 80 77 76
        70 71 73 74 77 78 81 82
        24 26 27 29 30 32
        32 30 28 26 25 23 21 20
        36 38 39 41 43
        32 29 26 25 23 22 19 18
        40 37 34 33 32 31 29
        81 84 85 87 89
        20 22 23 25 28
        53 50 49 48 47
        48 49 50 53 55 56
        18 17 14 13 12 9 6 4
        14 12 9 8 5 4 3
        9 10 11 12 13 16 18
        50 48 47 45 44 41 40 37
        46 48 50 52 53
        21 20 19 18 16
        28 26 23 21 19
        82 80 79 76 73 72 70
        70 69 66 65 64 63
        83 86 87 90 92 95
        46 49 52 55 58 59 62 63
        30 27 24 22 19 18 16
        19 17 14 12 11 9
        48 51 54 55 56 59 61 63
        17 16 15 14 12 11
        71 68 66 64 63 61 59
        74 73 72 69 66 64 61 60
        38 39 41 42 44
        38 35 32 31 29
        53 52 51 48 46 44 41 39
        23 22 20 17 15 14
        39 38 37 35 32 30 27 24
        60 59 57 54 52 49
        34 37 39 40 42
        76 79 82 85 88
        65 62 59 57 55 53 52 51
        20 22 24 27 28 31 33 36
        76 79 81 83 85 86
        37 34 32 30 27 24 23
        41 42 44 45 47 50
        6 8 9 10 11 12
        51 53 55 56 59
        65 62 60 58 57 55 54 52
        7 8 11 13 16 19 20 22
        49 46 45 44 42
        50 53 55 58 60 63 65 68
        63 60 58 57 54
        9 11 14 17 20 21 22
        37 36 35 32 31 30 29 28
        82 79 76 73 70 67 66 64
        8 11 12 15 18 20
        78 79 80 82 85 86
        96 93 90 89 88 86 84
        69 72 75 76 77 79 82
        50 47 44 43 42 41 38 35
        67 69 70 73 74 75 77
        52 53 55 58 59
        23 25 28 31 33 35 38
        77 78 79 80 82 83
        91 89 87 84 81
        17 14 11 8 6
        40 43 44 47 49 51 52 53
        62 65 68 69 71 74 76
        34 36 38 40 42 44
        86 83 80 79 76 73 72
        82 83 84 87 90 92 94 97
        14 15 16 17 18 19 20
        77 80 83 84 87 88
        80 77 75 72 70 69 67 66
        44 42 40 38 36
        39 40 41 44 46 48
        17 15 13 12 9
        54 56 57 60 63
        86 89 91 93 96
        36 33 31 29 27 25 23 21
        66 63 60 59 56 53 52 49
        79 81 83 86 89 90 91
        36 35 32 31 29 28 26 24
        80 82 83 85 87
        45 48 51 52 55 57 58 61
        90 89 88 86 83 80
        28 29 31 32 34 36 37
        7 10 11 13 15
        78 81 84 87 89 90 91 92
        73 74 77 80 82 83
        94 93 92 90 87 86 83 80
        72 73 75 78 81 82 84 86
        2 3 4 7 8
        77 79 80 81 82 83
        67 66 65 62 59 56 55 53
        37 38 41 43 46 47
        57 59 62 65 67
        78 80 81 83 86 88 90
        12 15 16 17 18 20 23 25
        59 58 57 55 53 51
        80 82 84 85 87 90
        60 61 62 64 66 68 69 70
        99 96 94 91 90
        80 77 76 74 72 71 69
        44 45 48 51 53 54 56
        22 23 25 26 29 30
        40 39 36 34 32
        11 10 7 6 5
        46 49 50 52 55 57
        83 85 88 90 93
        57 58 60 63 66
        76 78 81 82 85
        5 7 8 10 11 13 15 16
        24 25 26 28 30 32 34
        53 54 55 57 59
        34 37 38 41 43 46 47 49
        68 70 73 74 77 79
        41 39 36 35 33 30 27 26
        62 59 58 56 53 52 49
        27 25 24 22 20
        3 6 9 11 14 17 18
        51 48 45 43 41 40 38 36
        99 98 97 95 92 90 87
        22 19 18 16 14 11 8
        13 15 18 21 24 26 27
        75 73 72 71 68 65
        97 95 92 91 88
        65 66 69 71 72 73 74
        46 48 49 52 55 58
        43 42 40 39 37 35 33 31
        19 18 15 13 12 11 10 7
        63 66 68 70 73 75
        60 62 65 68 71 72 73 75
        97 96 93 91 88 86
        74 75 77 80 81 82
        52 53 56 57 59 60 61 63
        16 19 20 23 25
        44 43 40 39 37 35
        25 23 22 20 19 17 14
        22 20 19 17 16 15 12 10
        32 30 28 27 24
        44 45 48 51 52 53 54 56
        71 72 75 78 80
        65 67 70 71 73 75 77 79
        25 27 29 32 35 37 39 42
        66 64 61 60 59
        65 64 61 60 59 57
        72 71 68 66 65 62
        68 71 74 75 76 77
        19 16 13 11 9
        48 45 43 42 40 38 37
        51 48 47 46 45 42 40 39
        43 44 47 48 49 52 55
        86 83 82 80 78
        80 78 75 72 70
        70 67 64 63 62 59 56 55
        96 94 92 89 88
        83 84 85 88 89 91
        80 81 82 83 85 88 90
        36 37 39 41 44 45
        41 43 44 45 47 48 51
        62 65 68 70 72 75 77 80
        83 84 87 88 89 92 95
        34 37 40 43 45 47
        62 60 58 56 54
        81 83 84 87 89 90 93
        98 96 95 93 92 91 89
        28 31 33 34 35 36 39 41
        35 34 32 30 29
        75 73 70 68 66 64 62 59
        17 18 21 24 25 27 30 32
        31 33 34 36 39
        67 66 65 63 62 59
        45 47 50 53 54
        8 9 11 13 15 16
        22 20 17 15 14 13 11
        81 84 87 89 90
        54 56 58 59 60 61 63
        51 52 55 57 59 62 65 67
        73 76 78 79 81 83 86 89
        94 93 90 89 88
        30 31 32 33 36 38 40
        46 44 41 40 38 35
        66 64 62 61 58 56 54
        14 16 19 22 24
        55 58 60 61 63 66 69 72
        67 66 65 63 61 58 55
        55 57 60 62 64 67 70
        25 28 31 33 35 37
        98 97 95 93 91 90
        58 61 63 65 67 69 70
        75 74 72 69 66
        71 74 75 78 79 80 81
        13 16 18 20 22 25 26 28
        84 86 87 89 92
        10 11 12 15 18 21 23 26
        91 88 86 83 81 78 76
        62 63 64 67 69 72
        32 31 30 27 24 21 20 17
        49 51 52 55 56 58 60
        76 77 79 82 83 86
        76 77 79 80 81 83 85 88
        17 20 22 25 26 29 31
        62 59 57 56 53
        12 10 7 6 5 4 3
        65 64 63 60 57 56 54
        90 91 92 95 97 98
        21 24 25 27 28 29 31
        33 34 37 38 40
        16 15 12 9 7
        71 73 75 78 79
        94 91 90 88 85 82
        80 77 75 72 69
        80 82 83 86 87 89 91 94
        89 91 92 94 95 98
        60 58 55 52 49 46
        44 45 47 49 52 55
        66 65 64 62 59 56 54 51
        49 50 51 52 53 54 56
        15 12 9 7 5 4 1
        19 17 16 14 11 8
        29 28 27 25 22 21 19
        23 20 18 15 13 10
        19 22 25 28 29 32
        55 53 52 51 48 47 45 44
        15 17 20 22 24 25 26
        52 53 56 58 59 62 65 67
        79 80 82 84 85 88 91
        3 6 7 8 11 12 13 16
        21 23 24 27 30 31
        37 40 41 42 43 45 47 50
        66 67 68 69 72
        51 52 53 54 55
        33 34 37 40 41 42 45
        85 84 83 80 77
        54 51 49 46 45 44
        96 93 91 90 87 84 81 80
        82 80 79 78 77 74 71 69
        49 47 46 43 41 40 39
        25 24 22 21 18
        98 95 93 92 89
        35 36 39 41 42 44
        45 44 42 39 38 35 34 31
        68 70 71 74 76 77 80 82
        44 46 48 49 52 55
        87 86 83 80 78 76
        78 76 75 72 70 69 67 65
        56 59 61 62 63 66 69
        64 66 67 68 69
        78 80 82 85 88 90 93 96
        85 88 89 91 93 95 98 99
        33 34 36 38 39
        34 33 31 30 27 25 23 20
        16 15 13 12 9 7 6
        62 59 57 56 55
        72 71 68 65 64 61 60
        6 7 8 10 11 14 17
        78 79 80 83 84 87 88 91
        66 69 70 72 75 76 77 79
        99 96 95 93 92 90 87 84
        4 6 8 11 12 14 16
        39 36 35 32 30 29
        93 91 88 85 84
        31 33 34 36 38 40 43
        59 56 55 54 53 50 47 44
        14 16 18 21 24 27 30 31
        33 30 28 26 24
        83 82 79 76 73
        44 41 40 39 38 37 36 33
        5 6 9 11 14 16 17 19
        11 12 15 16 18 20 23
        33 31 30 28 25 22 20
        43 42 40 39 37 34 31 29
        1 4 7 8 10 13
        30 32 34 35 37 39
        37 39 42 45 46 49 51 52
        72 75 77 78 79 80 82
        85 83 80 77 76 74 72 70
        60 57 56 54 52 51
        62 61 60 57 54
        85 82 80 78 75
        42 45 47 48 50 51 52 54
        40 42 43 46 47 50
        44 47 49 50 51
        42 45 47 48 49 52 53
        24 26 28 29 31 33
        28 25 23 22 19 16
        14 13 11 9 6 3 1
        68 66 63 60 57 56 54 51
        3 4 5 8 11 14 16
        41 43 44 45 48 50 53 55
        62 64 67 70 72 74 76
        16 13 11 10 7 5 3 1
        87 86 83 82 79
        26 29 30 32 35 36 37
        58 61 62 64 67
        83 82 79 76 73 70
        65 67 69 70 72 73 75 77
        64 65 66 67 69 71
        1 3 5 7 9
        51 53 54 55 58 61
        19 16 15 13 11 10 7 5
        82 79 76 74 71 68 67 64
        32 30 28 27 25 22 20
        71 72 73 76 78 81
        45 43 40 38 37 35 34 31
        35 34 32 31 28
        16 17 18 21 22 25 28 29
        24 23 22 21 18 17 16 13
        54 56 59 60 63 66 68
        56 59 61 64 65
        63 62 59 56 54
        43 45 48 50 52 55 56
        80 77 76 75 73 71
        35 36 39 42 45 47 49
        6 9 12 15 18 20 23
        71 73 76 77 80 83 85
        57 59 61 63 64 67 68 70
        30 28 25 22 21 18 15 14
        70 67 64 63 61 58 57
        26 24 21 20 18 17 15 13
        60 59 56 54 51 48 47 45
        93 95 96 98 99
        91 88 86 84 81 80 79 76
        12 10 9 6 3
        22 19 16 15 14 11 8
        40 39 38 35 32
        64 62 60 58 56 55
        31 33 35 38 40 42
        18 17 14 11 9 6 5 2
        82 85 87 88 90 91
        65 64 61 58 57 54
        28 30 32 33 34 35 38
        58 55 54 51 50 47 45 42
        31 30 28 25 22 19 18
        53 55 56 59 61
        16 15 13 10 8 5 3 1
        34 37 40 43 45 47 50 53
        38 35 34 31 29
        20 17 16 14 11
        27 24 21 20 18
        79 80 83 84 87 88 89
        30 29 26 24 22 21
        47 50 53 56 58 61
        77 74 72 69 66
        78 77 75 72 70 67 65 64
        79 77 74 73 70 69 67 64
        54 56 58 59 61 64
        87 85 82 80 79
        59 60 63 65 68 71 72
        82 83 86 89 91 94 97 98
        15 14 12 9 7 6 4 3
        24 25 27 29 31 32 35
        68 65 64 61 58 57
        31 34 36 38 40 41
        49 51 52 55 56 58 61 64
        15 14 13 10 7
        88 87 86 84 82 80 79 78
        60 61 64 65 68 69
        90 92 94 97 99
        62 64 66 68 70 72 73 74
        34 35 36 38 41 43 45
        25 27 29 30 31 33 34
        80 81 82 84 85 86
        22 19 16 15 13 11 10
        45 48 49 52 53 54 57
        60 57 55 52 51 48 45
        95 92 89 88 86 84
        51 50 48 46 43 40 39
        60 58 57 55 53 50 47
        69 67 65 63 60 57
        37 40 42 43 44 46 49 51
        11 14 16 18 21 22 25
        51 48 46 45 44 41 40
        80 81 82 84 87 90
        46 44 42 40 37 35 32 29
        62 63 65 67 68 71 74
        5 7 8 11 13 16 19 20
        19 16 13 10 9
        45 48 49 51 53 54 55 56
        3 4 5 6 8 11 12 15
        44 47 48 51 54 57 60
        83 82 80 77 74 72 71 68
        69 68 65 63 62 61 59 56
        14 11 8 6 4
        82 83 86 87 90 92
        62 59 57 56 55 54 53
        64 65 67 70 72 75
        48 47 44 42 41 40 38 36
        26 25 23 20 19 16
        61 59 58 55 54 52
        70 73 75 76 77 78 79
        68 71 72 74 77 78 80 83
        69 70 71 72 75 76 78 80
        84 85 87 90 91 94 96 99
        2 3 5 8 9
        27 29 31 33 34 37 38
        65 63 61 58 57 56
        59 57 54 53 51 49
        46 47 49 51 54 57
        66 68 70 72 74 77 80 81
        55 53 51 48 46 43
        84 83 80 79 76 75 73
        60 63 66 68 71 72 74 77
        82 84 85 86 88 89 91
        51 53 55 58 61 62 65 66
        55 57 59 61 64 65
        41 42 44 47 48 50
        67 64 63 61 59 58 55 52
        20 22 25 26 28 31 32
        32 35 38 41 43 46
        13 10 9 8 6 3 1
        31 28 27 26 23 20
        61 60 59 57 55
        77 80 83 86 89 90
        29 26 25 22 19
        32 34 37 39 42 43
        76 74 73 71 69 66
        23 26 28 29 32 34 36 37
        34 33 32 30 29 27 24 22
        62 59 57 56 55 52 49 46
        63 62 60 58 57 54
        52 54 57 60 62 65
        1 4 5 6 7
        8 9 12 15 17 19 21
        16 18 21 23 26 29
        72 69 68 67 65 62 60 59
        51 50 47 45 44 41 40 39
        27 24 22 19 18 17
        21 22 24 27 28 31 33
        84 83 80 79 78 76 74 73
        84 85 86 88 90 92 93
        38 39 41 44 47 49 52
        3 6 9 10 13 14 15
        14 12 11 9 6 4
        79 77 74 71 69 67 66 64
        55 54 53 50 49 47 45
        54 55 56 58 60 62 63 65
        41 44 45 48 49 52
        75 77 78 79 81 83 86
        55 57 58 59 61 63
        97 96 94 93 90 87
        78 80 82 84 85 88 89 91
        57 54 51 50 48 47 45 43
        66 65 63 60 59 56
        26 24 23 22 19 17 16 14
        46 43 40 39 37
        71 69 66 63 61
        68 66 64 61 58 55 53 51
        68 65 63 60 58 57 55
        65 68 69 72 74 76 77 79
        82 81 78 75 72 69 66 65
        79 76 73 71 68 67 66
        21 23 24 25 26
        39 36 34 31 30 28 25 24
        6 9 10 12 15 16
        33 30 27 25 23 20 19 16
        46 48 50 52 54
        75 77 79 81 84 85 86 89
        83 84 85 87 88 90 93
        43 45 46 48 51 54 56 58
        29 32 35 38 40
        29 27 26 24 22
        41 38 35 33 30 27 26
        42 43 45 48 50 51 54 55
        93 91 90 89 88 85 83
        10 13 15 17 20
        62 64 67 68 69
        18 17 15 13 10 8 7
        44 46 49 51 54
        41 40 37 34 31 29
        89 92 94 96 97 99
        81 79 76 73 71 69 66 63
        46 49 52 55 58 60 61 63
        40 41 43 44 46 48
        67 69 72 73 75 76 77
        90 87 85 84 81
        8 7 4 2 1
        35 36 37 39 41 43
        72 74 75 76 77 78 80
        87 84 83 80 78 75 73
        78 80 82 85 88 90 91
        57 54 52 51 49 48 45 42
        30 33 34 36 39 40 43
        52 55 58 60 62 64
        72 69 67 66 64 63
        98 95 92 90 88 87 85 82
        96 94 91 89 87 86
        42 41 38 36 34 32 29 28
        73 76 79 82 85 87
        30 32 33 36 39 41 42
        75 72 69 68 67 66 65 64
        94 93 90 87 86 84
        32 34 36 38 40 43 46
        29 26 25 24 21
        89 92 93 94 95 97
        69 72 74 75 77 78 79 81
        77 74 73 72 70 69
        67 65 62 60 59
        11 13 15 17 18 21 22
        21 19 16 15 12 9 6 3
        90 87 85 83 80 78 75
        27 30 31 34 35 36 38
        54 55 57 58 60 62 65 67
        38 41 44 46 49
        24 25 26 29 32
        80 79 76 73 72
        73 70 68 65 63 61 58
        28 26 23 20 17 16 14
        36 34 31 28 26 23
        66 64 63 62 59 58
    """.trimIndent()

    // Count and print the number of safe reports
    val safeReports = countSafeReports(input)
    println("The number of safe reports is: $safeReports")
}
