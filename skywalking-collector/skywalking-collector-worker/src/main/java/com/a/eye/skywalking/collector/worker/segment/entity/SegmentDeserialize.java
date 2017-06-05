package com.a.eye.skywalking.collector.worker.segment.entity;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pengys5
 */
public enum SegmentDeserialize {
    INSTANCE;

    public Segment deserializeSingle(String singleSegmentJsonStr) throws IOException {
        SegmentJsonReader reader = new SegmentJsonReader(new StringReader(singleSegmentJsonStr));
        Segment segment = new Segment();
        segment.deserialize(reader);
        return segment;
    }

    public List<Segment> deserializeMultiple(String segmentJsonFile) throws Exception {
        List<Segment> segmentList = new ArrayList<>();
        streamReader(segmentList, new FileReader(segmentJsonFile));
        return segmentList;
    }

    private void streamReader(List<Segment> segmentList, FileReader fileReader) throws Exception {
        try (SegmentJsonReader reader = new SegmentJsonReader(fileReader)) {
            readSegmentArray(segmentList, reader);
        }
    }

    private void readSegmentArray(List<Segment> segmentList, SegmentJsonReader reader) throws Exception {
        reader.beginArray();
        while (reader.hasNext()) {
            Segment segment = new Segment();
            segment.deserialize(reader);
            segmentList.add(segment);
        }
        reader.endArray();
    }
}