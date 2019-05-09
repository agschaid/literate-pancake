package agschaid.reactivepg.dtos;

import java.util.UUID;

public class Contract
{

    private UUID id;

    private int zeMoney;

    private String zeText;

    public UUID getId()
    {
        return id;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    public int getZeMoney()
    {
        return zeMoney;
    }

    public void setZeMoney(int zeMoney)
    {
        this.zeMoney = zeMoney;
    }

    public String getZeText()
    {
        return zeText;
    }

    public void setZeText(String zeText)
    {
        this.zeText = zeText;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Contract))
        {
            return false;
        }

        Contract contract = (Contract) o;

        if (zeMoney != contract.zeMoney)
        {
            return false;
        }
        if (id != null ? !id.equals(contract.id) : contract.id != null)
        {
            return false;
        }
        return zeText != null ? zeText.equals(contract.zeText) : contract.zeText == null;
    }

    @Override
    public int hashCode()
    {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + zeMoney;
        result = 31 * result + (zeText != null ? zeText.hashCode() : 0);
        return result;
    }
}
