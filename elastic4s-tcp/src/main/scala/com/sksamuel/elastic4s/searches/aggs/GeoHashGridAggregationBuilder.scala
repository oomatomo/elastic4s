package com.sksamuel.elastic4s.searches.aggs

import com.sksamuel.elastic4s.searches.aggs.pipeline.PipelineAggregationBuilderFn
import org.elasticsearch.search.aggregations.AggregationBuilders
import org.elasticsearch.search.aggregations.bucket.geogrid.GeoGridAggregationBuilder

import scala.collection.JavaConverters._

object GeoHashGridAggregationBuilder {

  def apply(agg: GeoHashGridAggregationDefinition): GeoGridAggregationBuilder = {

    val builder = AggregationBuilders.geohashGrid(agg.name)

    agg.field.foreach(builder.field)
    agg.precision.foreach(builder.precision)
    agg.shardSize.foreach(builder.shardSize)
    agg.size.foreach(builder.size)

    agg.subaggs.map(AggregationBuilder.apply).foreach(builder.subAggregation)
    agg.pipelines.map(PipelineAggregationBuilderFn.apply).foreach(builder.subAggregation)
    if (agg.metadata.nonEmpty) builder.setMetaData(agg.metadata.asJava)
    builder
  }
}
