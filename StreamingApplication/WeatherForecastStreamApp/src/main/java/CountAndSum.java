/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class CountAndSum extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 4261338041276817023L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"CountAndSum\",\"fields\":[{\"name\":\"count\",\"type\":\"int\"},{\"name\":\"sum\",\"type\":\"double\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<CountAndSum> ENCODER =
      new BinaryMessageEncoder<>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<CountAndSum> DECODER =
      new BinaryMessageDecoder<>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<CountAndSum> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<CountAndSum> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<CountAndSum> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this CountAndSum to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a CountAndSum from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a CountAndSum instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static CountAndSum fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private int count;
  private double sum;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public CountAndSum() {}

  /**
   * All-args constructor.
   * @param count The new value for count
   * @param sum The new value for sum
   */
  public CountAndSum(java.lang.Integer count, java.lang.Double sum) {
    this.count = count;
    this.sum = sum;
  }

  @Override
  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }

  @Override
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }

  // Used by DatumWriter.  Applications should not call.
  @Override
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return count;
    case 1: return sum;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @Override
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: count = (java.lang.Integer)value$; break;
    case 1: sum = (java.lang.Double)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'count' field.
   * @return The value of the 'count' field.
   */
  public int getCount() {
    return count;
  }


  /**
   * Sets the value of the 'count' field.
   * @param value the value to set.
   */
  public void setCount(int value) {
    this.count = value;
  }

  /**
   * Gets the value of the 'sum' field.
   * @return The value of the 'sum' field.
   */
  public double getSum() {
    return sum;
  }


  /**
   * Sets the value of the 'sum' field.
   * @param value the value to set.
   */
  public void setSum(double value) {
    this.sum = value;
  }

  /**
   * Creates a new CountAndSum RecordBuilder.
   * @return A new CountAndSum RecordBuilder
   */
  public static CountAndSum.Builder newBuilder() {
    return new CountAndSum.Builder();
  }

  /**
   * Creates a new CountAndSum RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new CountAndSum RecordBuilder
   */
  public static CountAndSum.Builder newBuilder(CountAndSum.Builder other) {
    if (other == null) {
      return new CountAndSum.Builder();
    } else {
      return new CountAndSum.Builder(other);
    }
  }

  /**
   * Creates a new CountAndSum RecordBuilder by copying an existing CountAndSum instance.
   * @param other The existing instance to copy.
   * @return A new CountAndSum RecordBuilder
   */
  public static CountAndSum.Builder newBuilder(CountAndSum other) {
    if (other == null) {
      return new CountAndSum.Builder();
    } else {
      return new CountAndSum.Builder(other);
    }
  }

  /**
   * RecordBuilder for CountAndSum instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<CountAndSum>
    implements org.apache.avro.data.RecordBuilder<CountAndSum> {

    private int count;
    private double sum;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(CountAndSum.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.count)) {
        this.count = data().deepCopy(fields()[0].schema(), other.count);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.sum)) {
        this.sum = data().deepCopy(fields()[1].schema(), other.sum);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
    }

    /**
     * Creates a Builder by copying an existing CountAndSum instance
     * @param other The existing instance to copy.
     */
    private Builder(CountAndSum other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.count)) {
        this.count = data().deepCopy(fields()[0].schema(), other.count);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.sum)) {
        this.sum = data().deepCopy(fields()[1].schema(), other.sum);
        fieldSetFlags()[1] = true;
      }
    }

    /**
      * Gets the value of the 'count' field.
      * @return The value.
      */
    public int getCount() {
      return count;
    }


    /**
      * Sets the value of the 'count' field.
      * @param value The value of 'count'.
      * @return This builder.
      */
    public CountAndSum.Builder setCount(int value) {
      validate(fields()[0], value);
      this.count = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'count' field has been set.
      * @return True if the 'count' field has been set, false otherwise.
      */
    public boolean hasCount() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'count' field.
      * @return This builder.
      */
    public CountAndSum.Builder clearCount() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'sum' field.
      * @return The value.
      */
    public double getSum() {
      return sum;
    }


    /**
      * Sets the value of the 'sum' field.
      * @param value The value of 'sum'.
      * @return This builder.
      */
    public CountAndSum.Builder setSum(double value) {
      validate(fields()[1], value);
      this.sum = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'sum' field has been set.
      * @return True if the 'sum' field has been set, false otherwise.
      */
    public boolean hasSum() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'sum' field.
      * @return This builder.
      */
    public CountAndSum.Builder clearSum() {
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CountAndSum build() {
      try {
        CountAndSum record = new CountAndSum();
        record.count = fieldSetFlags()[0] ? this.count : (java.lang.Integer) defaultValue(fields()[0]);
        record.sum = fieldSetFlags()[1] ? this.sum : (java.lang.Double) defaultValue(fields()[1]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<CountAndSum>
    WRITER$ = (org.apache.avro.io.DatumWriter<CountAndSum>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<CountAndSum>
    READER$ = (org.apache.avro.io.DatumReader<CountAndSum>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeInt(this.count);

    out.writeDouble(this.sum);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.count = in.readInt();

      this.sum = in.readDouble();

    } else {
      for (int i = 0; i < 2; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.count = in.readInt();
          break;

        case 1:
          this.sum = in.readDouble();
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










