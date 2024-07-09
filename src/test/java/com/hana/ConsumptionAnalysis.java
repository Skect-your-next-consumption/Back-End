package com.hana;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.*;

public class ConsumptionAnalysis {
    public static class Item implements Comparable<Item> {
        String name;
        double average;
        double standardAverage;
        double coefficientOfVariation;
        double weight;

        public Item(String name, double average, double standardAverage, double coefficientOfVariation) {
            this.name = name;
            this.average = average;
            this.standardAverage = standardAverage;
            this.coefficientOfVariation = coefficientOfVariation;
        }

        @Override
        public int compareTo(Item other) {
            if (this.average > this.standardAverage && other.average > other.standardAverage) {
                return Double.compare(this.coefficientOfVariation, other.coefficientOfVariation);
            } else if (this.average <= this.standardAverage && other.average <= other.standardAverage) {
                return Double.compare(this.coefficientOfVariation, other.coefficientOfVariation);
            } else {
                return Double.compare(other.average, this.average);
            }
        }
    }

    public static double calculateMean(List<Double> data) {
        double sum = 0;
        for (double value : data) {
            sum += value;
        }
        return sum / data.size();
    }

    public static double calculateVariance(List<Double> data, double mean) {
        double sumSquaredDiff = 0;
        for (double value : data) {
            sumSquaredDiff += Math.pow(value - mean, 2);
        }
        return sumSquaredDiff / data.size();
    }

    public static double calculateStandardDeviation(double variance) {
        return Math.sqrt(variance);
    }

    public static double calculateCoefficientOfVariation(double standardDeviation, double mean) {
        return standardDeviation / mean;
    }

    // calculateMean, calculateVariance, calculateStandardDeviation, calculateCoefficientOfVariation 메소드는 이전과 동일

    public static Map<String, Double> analyzeConsumption(String jsonData, Map<String, Double> standardAverages) {
        Gson gson = new Gson();
        List<Map<String, Double>> dataList = gson.fromJson(jsonData, new TypeToken<List<Map<String, Double>>>(){}.getType());

        Map<String, List<Double>> dataSet = new HashMap<>();

        // 데이터 처리
        for (Map<String, Double> data : dataList) {
            for (Map.Entry<String, Double> entry : data.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue();
                dataSet.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
            }
        }

        List<Item> items = new ArrayList<>();



        for (Map.Entry<String, List<Double>> entry : dataSet.entrySet()) {
            String name = entry.getKey();
            List<Double> data = entry.getValue();

            double mean = calculateMean(data);
            double variance = calculateVariance(data, mean);
            double standardDeviation = calculateStandardDeviation(variance);
            double coefficientOfVariation = calculateCoefficientOfVariation(standardDeviation, mean);

            double standardAverage = standardAverages.getOrDefault(name, 0.0);

            items.add(new Item(name, mean, standardAverage, coefficientOfVariation));
        }

        // 정렬
        Collections.sort(items);

        // 결과 Map 생성
        Map<String, Double> result = new LinkedHashMap<>();
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            item.weight = 1.0 - (double)i / (items.size() - 1);

            result.put(item.name, item.average);
            result.put(item.name + "_weight", item.weight);
        }

        return result;
    }

    public static void main(String[] args) {
        String jsonData = "[{\"카페\":0.04,\"오락\":0.04,\"택시\":0.01,\"쇼핑\":0.35,\"술\":0.04,\"배달\":0.14,\"야식\":0.07,\"구독서비스\":0.01}," +
                "{\"카페\":0.06,\"오락\":0.08,\"택시\":0.02,\"쇼핑\":0.30,\"술\":0.05,\"배달\":0.18,\"야식\":0.09,\"구독서비스\":0.02}," +
                "{\"카페\":0.03,\"오락\":0.05,\"택시\":0.01,\"쇼핑\":0.40,\"술\":0.06,\"배달\":0.12,\"야식\":0.05,\"구독서비스\":0.03}]";

        Map<String, Double> standardAverages = new HashMap<>();
        standardAverages.put("카페", 0.04);
        standardAverages.put("오락", 0.04);
        standardAverages.put("택시", 0.01);
        standardAverages.put("쇼핑", 0.35);
        standardAverages.put("술", 0.04);
        standardAverages.put("배달", 0.14);
        standardAverages.put("야식", 0.07);
        standardAverages.put("구독서비스", 0.01);

        Map<String, Double> result = analyzeConsumption(jsonData, standardAverages);
        System.out.println(result);
    }
}